package unicam.ids.hackhub.core.sottomissioni;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Valutazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valutazioneId;

    private Double voto;

    private String descrizione;

    @OneToOne
    @JoinColumn(name = "sottomissione_id")
    private Sottomissione sottomissione;

    public Valutazione() {
    }

    public Valutazione(Double voto, String descrizione, Sottomissione sottomissione) {
        this.voto = voto;
        this.descrizione = descrizione;
        this.sottomissione = sottomissione;
    }

    public Long getValutazioneId() {
        return valutazioneId;
    }

    public void setValutazioneId(Long valutazioneId) {
        this.valutazioneId = valutazioneId;
    }

    public Double getVoto() {
        return voto;
    }

    public void setVoto(Double voto) {
        this.voto = voto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Sottomissione getSottomissione() {
        return sottomissione;
    }

    public void setSottomissione(Sottomissione sottomissione) {
        this.sottomissione = sottomissione;
    }
}