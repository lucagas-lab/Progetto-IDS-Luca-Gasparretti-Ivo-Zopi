package unicam.ids.hackhub.dto;

public class ValutaSottomissioneDTO {

    private Long idSottomissione;
    private Double voto;
    private String descrizione;

    public ValutaSottomissioneDTO() {
    }

    public ValutaSottomissioneDTO(Long idSottomissione, Double voto, String descrizione) {
        this.idSottomissione = idSottomissione;
        this.voto = voto;
        this.descrizione = descrizione;
    }

    public Long getIdSottomissione() {
        return idSottomissione;
    }

    public void setIdSottomissione(Long idSottomissione) {
        this.idSottomissione = idSottomissione;
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
}