package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.segnalazioni.Segnalazione;
import unicam.ids.hackhub.core.team.Team;

import java.util.List;

@Repository
public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

    //List<Segnalazione> findByHackathon(Hackathon hackathon);

    //List<Segnalazione> findByHackathon_IdHackathon(Long idHackathon);

    //List<Segnalazione> findByTeam(Team team);
}