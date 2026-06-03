package unicam.ids.hackhub.infrastructure;

import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.sottomissioni.Sottomissione;

import java.util.Optional;

public interface SottomissioneRepository {
    Optional<Sottomissione> findByNomeTeam(Team nomeTeam);
    Optional<Sottomissione> findByNomeTeamID(Long teamid);

    boolean existsByTeam(Team t);

    void deleteByTeam(Team t);
}
