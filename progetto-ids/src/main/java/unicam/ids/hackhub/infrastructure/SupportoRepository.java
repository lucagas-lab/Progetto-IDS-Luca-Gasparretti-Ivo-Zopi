package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.supporto.Supporto;
import java.util.List;

@Repository
public interface SupportoRepository extends JpaRepository<Supporto, Long> {

    //List<Supporto> findByHackathon_IdHackathon(Long idHackathon);
}
