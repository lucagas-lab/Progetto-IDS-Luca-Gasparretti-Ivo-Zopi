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

    // L'injection tramite costruttore garantisce che Spring carichi tutti i repository necessari
    public GestoreRichiesta(RichiestaRepository richiestaRep,
                            UtenteRepository utenteRep,
                            TeamRepository teamRep) {
        this.richiestaRep = richiestaRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    public void richiediAccessoAlTeam(String username, Long idTeam) throws Exception {
        // 1. Recupero l'utente e il team dai rispettivi repository
        Utente utente = utenteRep.findByUsername(username)
                .orElseThrow(() -> new Exception("Errore: Utente non trovato."));

        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        // 2. Controllo di business: l'utente fa già parte di un team?
        if (teamRep.findByUtentiUsername(username).isPresent()) {
            throw new Exception("Errore: Fai già parte di un team, non puoi inviare richieste ad altri gruppi.");
        }

        // 3. Controllo anti-spam: esiste già una richiesta che NON sia stata rifiutata?
        // Questo blocca le richieste se sono ancora PENDENTI o già ACCETTATE
        if (richiestaRep.existsByUtenteAndTeamAndStatoRichiestaNot(utente, team, EsitoRichiesta.RIFIUTATA)) {
            throw new Exception("Errore: Hai già una richiesta attiva per questo team.");
        }

        // 4. Se tutti i controlli passano, creo la nuova richiesta (che nasce PENDENTE di default)
        Richiesta nuovaRichiesta = new Richiesta(utente, team);

        // 5. Salvo nel database
        richiestaRep.save(nuovaRichiesta);
    }

    public void accettaRichiestaAccessoUtente(Long idRichiesta) throws Exception {
        // 1. Recupero la richiesta dal database
        Richiesta richiesta = richiestaRep.findById(idRichiesta)
                .orElseThrow(() -> new Exception("Errore: Richiesta non trovata con ID " + idRichiesta));

        // 2. Controllo che la richiesta sia effettivamente PENDENTE
        if (richiesta.getStato() != EsitoRichiesta.PENDENTE) {
            throw new Exception("Errore: Questa richiesta è già stata " + richiesta.getStato().name().toLowerCase() + ".");
        }

        Utente utente = richiesta.getUtente();
        Team team = richiesta.getTeam();

        // 3. Controllo Edge Case: l'utente si è unito a un altro team mentre aspettava?
        if (teamRep.findByUtentiUsername(utente.getUsername()).isPresent()) {
            // Se fa già parte di un altro team, cambiamo lo stato in RIFIUTATA in automatico
            richiesta.setStato(EsitoRichiesta.RIFIUTATA);
            richiestaRep.save(richiesta);
            throw new Exception("Errore: L'utente è già entrato in un altro team nel frattempo. Richiesta archiviata.");
        }

        // 4. Modifico lo stato della richiesta
        richiesta.setStato(EsitoRichiesta.ACCETTATA);

        // 5. Aggiungo l'utente alla lista dei membri del team
        team.addUtente(utente); // Attenzione: assicurati di avere questo metodo nella classe Team!

        // 6. Salvo le modifiche sul database
        richiestaRep.save(richiesta);
        teamRep.save(team);
    }

    public List<RichiestaDTO> getRichiestePendenti(Long idTeam) throws Exception {
        // 1. Verifichiamo che il team esista
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        // 2. Recuperiamo TUTTE le richieste associate a questo team
        List<Richiesta> tutteLeRichieste = richiestaRep.findByTeam(team);

        // 3. Filtriamo solo quelle PENDENTI e le convertiamo nel nostro nuovo DTO
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