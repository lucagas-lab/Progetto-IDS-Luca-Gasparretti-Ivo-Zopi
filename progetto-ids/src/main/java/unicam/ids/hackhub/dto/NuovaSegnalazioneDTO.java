package unicam.ids.hackhub.dto;

public class NuovaSegnalazioneDTO {

    private Long idTeamSospettato;
    private String descrizioneViolazione;

    public NuovaSegnalazioneDTO() {}

    public Long getIdTeamSospettato() {
        return idTeamSospettato;
    }

    public void setIdTeamSospettato(Long idTeamSospettato) {
        this.idTeamSospettato = idTeamSospettato;
    }

    public String getDescrizioneViolazione() {
        return descrizioneViolazione;
    }

    public void setDescrizioneViolazione(String descrizioneViolazione) {
        this.descrizioneViolazione = descrizioneViolazione;
    }
}