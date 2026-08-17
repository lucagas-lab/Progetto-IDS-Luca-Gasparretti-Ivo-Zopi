package unicam.ids.hackhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.hackhub.core.supporto.Supporto;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.dto.SupportoDTO;
import unicam.ids.hackhub.infrastructure.HackathonRepository;
import unicam.ids.hackhub.infrastructure.SupportoRepository;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GestoreSupporto {

    private final SupportoRepository supportoRep;
    private final UtenteRepository utenteRep;
    private final HackathonRepository hackathonRep;

    public GestoreSupporto(SupportoRepository supportoRep, UtenteRepository utenteRep, HackathonRepository hackathonRep) {
        this.supportoRep = supportoRep;
        this.utenteRep = utenteRep;
        this.hackathonRep = hackathonRep;
    }

    public void inviaRichiestaSupporto(String usernameUtente, String testoRichiesta) throws Exception {
        if (testoRichiesta == null || testoRichiesta.trim().isEmpty()) {
            throw new Exception("Errore: Il testo della richiesta non può essere vuoto.");
        }

        Utente utente = utenteRep.findByUsername(usernameUtente)
                .orElseThrow(() -> new Exception("Errore: Utente non trovato."));

        Team team = utente.getTeam();
        if (team == null) {
            throw new Exception("Errore: Non appartieni a nessun team, impossibile richiedere supporto.");
        }

        Hackathon hackathon = team.getHackathon();
        if (hackathon == null) {
            throw new Exception("Errore: Il tuo team non è iscritto a nessun hackathon.");
        }

        Supporto supporto = new Supporto(team, testoRichiesta, "APERTA");
        supportoRep.save(supporto);
    }

    public List<SupportoDTO> visualizzaRichiestaSupporto(Long idHackathon) throws Exception {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: Hackathon non trovato."));
        List<Supporto> richieste = supportoRep.findAll().stream()
                .filter(s -> s.getTeam().getHackathon() != null && s.getTeam().getHackathon().getHackathonId().equals(idHackathon))
                .collect(Collectors.toList());

        return richieste.stream()
                .map(s -> new SupportoDTO(
                        s.getIdSupporto(),
                        s.getTeam().getNomeTeam(),
                        s.getTestoRichiesta(),
                        s.getStato()))
                .collect(Collectors.toList());
    }
}