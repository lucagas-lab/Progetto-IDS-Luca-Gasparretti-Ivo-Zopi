package unicam.ids.hackhub.core.sottomissioni;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import unicam.ids.hackhub.core.team.Team;

@Entity
public class Valutazione {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long valutazioneId;
    private Double voto;
    private String descrizione;

    public Valutazione(){}

    public Valutazione(Double voto, String descrizione){
        this.voto= voto;
        this.descrizione=descrizione;
    }

    public Long getValutazioneId(){ return valutazioneId; }

    public Double getVoto(){ return voto;}

    public void setVoto(Double voto){ this.voto=voto; }

    public String getDescrizione(){ return descrizione; }

    public void setDescrizione(String descrizione){ this.descrizione=descrizione; }
}
