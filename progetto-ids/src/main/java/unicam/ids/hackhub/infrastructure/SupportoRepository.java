package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import  unicam.ids.hackhub.core.supporto.Supporto;

import java.util.List;

public interface SupportoRepository {
    List<Supporto> findByHackathonID(Long hackathonId);
}
