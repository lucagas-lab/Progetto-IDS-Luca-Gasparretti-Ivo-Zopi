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

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long>{

    Optional<Hackathon> findByNomeHackathon(String nomeHackathon);

    List<Hackathon> findAll();

    void deleteByNomeHackathon(String nomeHackathon);

    List<Hackathon> findByStato(StatoHackathon statoHackathon);

    List<Hackathon> findByOrganizzatore(Utente organizzatore);

    List<Hackathon> findByGiudice(Utente giudice);

    Optional<Hackathon> findByNomeHackathonAndStato(String nomeHackathon, StatoHackathon statoHackathon);

    Optional<Hackathon> findByMentoriContaining(Utente mentore);
}
