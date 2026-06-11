package unicam.ids.hackhub.infrastructure;

import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.segnalazioni.Segnalazione;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;

public interface SegnalazioneRepository {
    List<Segnalazione> findByHackathon(Hackathon hackathon);
    List<Segnalazione> findByHackathonId(List<Hackathon> hackathons);
    List<Segnalazione> findByTeam(Team team);
}
