package unicam.ids.hackhub.infrastructure;

import unicam.ids.hackhub.core.team.Team;

import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findByUtentiUsername(String username);
    Optional<Team> findByNome(String nomeTeam);
}
