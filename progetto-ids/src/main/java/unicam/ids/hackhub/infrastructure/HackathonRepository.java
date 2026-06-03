package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.hackathon.StatoHackathon;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;
import java.util.Optional;

public interface HackathonRepository extends JpaRepository<Hackathon, Long>{
    Optional<Hackathon> findByNome(String nomeHackathon);
    List<Hackathon> findAll();
    void deleteByNome(String nomeHackathon);

    List<Hackathon> findByStatoHackathon(StatoHackathon statoHackathon);

    List<Hackathon> findByOrganizzatore(Utente organizzatore);

    Optional<Hackathon> findByNomeAndStatoHackathon(String nomeHackathon, StatoHackathon statoHackathon);

    Optional<Hackathon> findByMentoriContaining(Utente mentore);
}
