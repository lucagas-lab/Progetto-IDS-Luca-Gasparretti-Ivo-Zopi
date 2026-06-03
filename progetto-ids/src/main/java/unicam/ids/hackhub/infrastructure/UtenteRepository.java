package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsername(String username);

    Optional<Utente> findByEmail(String email);
}
