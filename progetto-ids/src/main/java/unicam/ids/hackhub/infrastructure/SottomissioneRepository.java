package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.sottomissioni.Sottomissione;

import java.util.Optional;

public interface SottomissioneRepository extends JpaRepository<Sottomissione, Long> {
    Optional<Sottomissione> findByNomeTeam(Team nomeTeam);
    Optional<Sottomissione> findByTeamID(Long teamId);

    boolean existsByTeam(Team t);

    void deleteByTeam(Team t);
}
