package unicam.ids.hackhub.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import unicam.ids.hackhub.core.sottomissioni.Valutazione;

public interface ValutazioneRepository extends JpaRepository<Valutazione, Long> {
}
