package unicam.ids.hackhub.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.dto.TeamDTO;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GestoreTeam {
    private final TeamRepository teamRep;
    private final UtenteRepository utenteRep;
    private final SegnalazioneRepository segnalazioneRep;
    private final HackathonRepository hackathonRep;
    private final SottomissioneRepository sottomissioneRep;

    public GestoreTeam(TeamRepository teamRepository, UtenteRepository utenteRep, SegnalazioneRepository segnalazioneRep,
                            HackathonRepository hackathonRep, SottomissioneRepository sottomissioneRep) {
        this.teamRep = teamRepository;
        this.utenteRep = utenteRep;
        this.segnalazioneRep = segnalazioneRep;
        this.hackathonRep = hackathonRep;
        this.sottomissioneRep = sottomissioneRep;
    }

    public void creaTeam(String username, String nomeTeam) throws Exception{
        if(teamRep.findByUtentiUsername(username).isPresent()){
            throw new Exception("Errore: Fai già parte di un team");
        }

        if(teamRep.findByNomeTeam(nomeTeam).isPresent()){
            throw new Exception("Errore: Esiste già un team con questo nome");
        }

        Utente utente= utenteRep.findByUsername(username).orElseThrow();
        Team team= new Team(nomeTeam, utente);
        teamRep.save(team);
    }

    public TeamDTO selezionaTeam(Long idTeam) throws Exception {
        Team selezioneTeam = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Il team selezionato non esiste: " + idTeam));

        return convertiDTO(selezioneTeam);
    }

    private TeamDTO convertiDTO(Team team) {
        // 1. Estrae il nome dell'hackathon (se iscritto)
        String nomeHackathon = (team.getHackathon() != null)
                ? team.getHackathon().getNomeHackathon()
                : "Nessun Hackathon";

        // 2. Estrae le liste di nomi ed email in modo sicuro
        List<String> nomiMembri = new ArrayList<>();
        List<String> emailMembri = new ArrayList<>();

        if (team.getUtenti() != null) {
            nomiMembri = team.getUtenti().stream()
                    .map(Utente::getUsername)
                    .collect(Collectors.toList());

            emailMembri = team.getUtenti().stream()
                    .map(Utente::getEmail)
                    .collect(Collectors.toList());
        }

        // 3. Ritorna il DTO usando il costruttore che hai appena creato
        return new TeamDTO(
                team.getTeamId(),
                team.getNomeTeam(),
                nomeHackathon,
                nomiMembri,
                emailMembri
        );
    }
}
