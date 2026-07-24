package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import unicam.ids.hackhub.core.sottomissioni.Valutazione;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, Long> {
}
