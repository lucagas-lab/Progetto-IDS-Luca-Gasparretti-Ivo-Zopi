package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.team.Team;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
    Optional<Team> findByUtentiUsername(String username);
    Optional<Team> findByNomeTeam(String nomeTeam);
}
