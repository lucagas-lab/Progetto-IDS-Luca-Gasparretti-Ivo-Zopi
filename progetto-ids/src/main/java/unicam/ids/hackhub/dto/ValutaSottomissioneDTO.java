package unicam.ids.hackhub.dto;

public class ValutaSottomissioneDTO {
    private Long idSottomissione;
    private Double voto;

    public ValutaSottomissioneDTO() {}

    public Long getIdSottomissione() { return idSottomissione; }
    public void setIdSottomissione(Long sottomissioneId) { this.idSottomissione = sottomissioneId; }

    public Double getVoto() { return voto; }
    public void setVoto(Double voto) { this.voto = voto; }
}
