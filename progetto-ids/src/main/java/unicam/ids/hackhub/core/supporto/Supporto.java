package unicam.ids.hackhub.core.supporto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Supporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupporto;

    public Supporto() {}
}