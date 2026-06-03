package unicam.ids.hackhub.infrastructure;

import import unicam.ids.hackhub.core.supporto.Supporto;

import java.util.List

public interface SupportoRepository {
    List<Supporto> findByHackathonID(Long hackathonId);
}
