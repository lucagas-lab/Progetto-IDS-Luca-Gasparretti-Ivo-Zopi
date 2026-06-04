package unicam.ids.hackhub.service;

import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.dto.TeamDTO;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.*;


public class GestoreTeam {
    private final TeamRepository teamRep;
    private final UtenteRepository utenteRep;
    private final SegnalazioneRepository segnalazioneRep;
    private final HackathonRepository hackathonRep;
    private final SottomissioneRepository sottomissioneRep;

    public GestoreTeam(TeamRepository teamRepository, UtenteRepository utenteRep, SegnalazioneRepository segnalazioneRep,
                            HackathonRepository hackathonRep, SottomissioneRepository sottomissioneRep){
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

        if(teamRep.findByNome(nomeTeam).isPresent()){
            throw new Exception("Errore: Esiste già un team con questo nome");
        }

        Utente utente= utenteRep.findByUsername(username).orElseThrow();
        Team team= new Team(nomeTeam, utente);
        teamRep.save(team);
    }
}
