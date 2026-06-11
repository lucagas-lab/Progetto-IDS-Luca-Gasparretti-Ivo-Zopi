package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.hackhub.core.richieste.EsitoRichiesta;
import unicam.ids.hackhub.core.richieste.Richiesta;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;

public interface RichiestaRepository extends JpaRepository<Richiesta, Long> {

    List<Richiesta> findByTeam(Team team);

    boolean existsByUtenteAndTeamAndEsitoRichiestaNot(Utente utente, Team team, EsitoRichiesta statoRichiesta);
}