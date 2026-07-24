package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.sottomissioni.Sottomissione;

import java.util.Optional;

@Repository
public interface SottomissioneRepository extends JpaRepository<Sottomissione, Long> {
    Optional<Sottomissione> findByTeam(Team team);

    Optional<Sottomissione> findByTeam_TeamId(Long teamId);

    Optional<Sottomissione> findByTeam_NomeTeam(String nomeTeam);

    boolean existsByTeam(Team t);

    void deleteByTeam(Team t);
}
