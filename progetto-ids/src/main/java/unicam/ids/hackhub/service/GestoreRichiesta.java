package unicam.ids.hackhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.hackhub.core.richieste.EsitoRichiesta;
import unicam.ids.hackhub.core.richieste.Richiesta;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.dto.RichiestaDTO;
import unicam.ids.hackhub.infrastructure.RichiestaRepository;
import unicam.ids.hackhub.infrastructure.TeamRepository;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

import java.util.stream.Collectors;
import java.util.List;

@Service
@Transactional
public class GestoreRichiesta {

    private final RichiestaRepository richiestaRep;
    private final UtenteRepository utenteRep;
    private final TeamRepository teamRep;


    public GestoreRichiesta(RichiestaRepository richiestaRep,
                            UtenteRepository utenteRep,
                            TeamRepository teamRep) {
        this.richiestaRep = richiestaRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    public void richiediAccessoAlTeam(String username, Long idTeam) throws Exception {
        Utente utente = utenteRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Errore: Utente non trovato."));

        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        if (teamRep.findByUtentiUsername(username).isPresent()) {
            throw new Exception("Errore: Fai già parte di un team, non puoi inviare richieste ad altri gruppi.");
        }

        if (richiestaRep.existsByUtenteAndTeamAndStatoRichiestaNot(utente, team, EsitoRichiesta.RIFIUTATA)) {
            throw new Exception("Errore: Hai già una richiesta attiva per questo team.");
        }

        Richiesta nuovaRichiesta = new Richiesta(utente, team);

        richiestaRep.save(nuovaRichiesta);
    }

    public void accettaRichiestaAccessoUtente(Long idRichiesta) throws Exception {
        Richiesta richiesta = richiestaRep.findById(idRichiesta)
                .orElseThrow(() -> new Exception("Errore: Richiesta non trovata con ID " + idRichiesta));

        if (richiesta.getStato() != EsitoRichiesta.PENDENTE) {
            throw new Exception("Errore: Questa richiesta è già stata " + richiesta.getStato().name().toLowerCase() + ".");
        }

        Utente utente = richiesta.getUtente();
        Team team = richiesta.getTeam();

        if (teamRep.findByUtentiUsername(utente.getUsername()).isPresent()) {
            richiesta.setStato(EsitoRichiesta.RIFIUTATA);
            richiestaRep.save(richiesta);
            throw new Exception("Errore: L'utente è già entrato in un altro team nel frattempo. Richiesta archiviata.");
        }

        richiesta.setStato(EsitoRichiesta.ACCETTATA);

        team.addUtente(utente);

        richiestaRep.save(richiesta);
        teamRep.save(team);
    }

    public List<RichiestaDTO> getRichiestePendenti(Long idTeam) throws Exception {
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        List<Richiesta> tutteLeRichieste = richiestaRep.findByTeam(team);

        return tutteLeRichieste.stream()
                .filter(richiesta -> richiesta.getStato() == EsitoRichiesta.PENDENTE)
                .map(richiesta -> new RichiestaDTO(
                        richiesta.getIdRichiesta(),
                        richiesta.getUtente().getUsername(),
                        richiesta.getDataRichiesta()
                ))
                .collect(Collectors.toList());
    }
}