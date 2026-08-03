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

    public TeamDTO visualizzaTeam(Long idTeam) throws Exception {
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));
        List<String> usernameMembri = team.getUtenti().stream()
                .map(Utente::getUsername)
                .collect(Collectors.toList());
        String nomeHackathon = (team.getHackathon() != null) ? team.getHackathon().getNomeHackathon() : "Nessun Hackathon";
        return new TeamDTO(team.getTeamId(), team.getNomeTeam(), nomeHackathon, usernameMembri);
    }

    public void modificaTeam(Long idTeam, String nuovoNome) throws Exception {
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
            if (teamRep.findByNomeTeam(nuovoNome).isPresent()) {
                throw new Exception("Errore: Esiste già un team con questo nome!");
            }
            team.setNomeTeam(nuovoNome);
            teamRep.save(team);
        }
    }

    public void abbandonaTeam(Long idTeam, String usernameUtente) throws Exception {
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));

        Utente utente = utenteRep.findByUsername(usernameUtente)
                .orElseThrow(() -> new Exception("Errore: Utente non trovato nel sistema."));

        team.rimuoviUtente(utente);
        utente.setTeam(null);

        teamRep.save(team);
        utenteRep.save(utente);

        if (team.getUtenti().isEmpty()) {
            teamRep.delete(team);
        }
    }

    public void eliminaTeam(Long idTeam) throws Exception {
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Team non trovato con ID " + idTeam));
        for (Utente u : team.getUtenti()) {
            u.setTeam(null);
            utenteRep.save(u);
        }
        teamRep.delete(team);
    }

    private TeamDTO convertiDTO(Team team) {
        String nomeHackathon = (team.getHackathon() != null)
                ? team.getHackathon().getNomeHackathon()
                : "Nessun Hackathon";

        List<String> nomiMembri = new ArrayList<>();

        if (team.getUtenti() != null) {
            nomiMembri = team.getUtenti().stream()
                    .map(Utente::getUsername)
                    .collect(Collectors.toList());

        }

        return new TeamDTO(
                team.getTeamId(),
                team.getNomeTeam(),
                nomeHackathon,
                nomiMembri
        );
    }
}
