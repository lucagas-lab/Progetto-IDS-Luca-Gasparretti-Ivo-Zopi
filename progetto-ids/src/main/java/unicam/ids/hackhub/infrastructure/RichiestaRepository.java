package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.richieste.EsitoRichiesta;
import unicam.ids.hackhub.core.richieste.Richiesta;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;

@Repository
public interface RichiestaRepository extends JpaRepository<Richiesta, Long> {

    List<Richiesta> findByTeam(Team team);

    boolean existsByUtenteAndTeamAndStatoRichiestaNot(Utente utente, Team team, EsitoRichiesta statoRichiesta);
}