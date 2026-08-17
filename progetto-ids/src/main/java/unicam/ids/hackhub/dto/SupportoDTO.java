package unicam.ids.hackhub.dto;

public class SupportoDTO {
    private Long idSupporto;
    private String nomeTeam;
    private String testoRichiesta;
    private String stato;

    public SupportoDTO(Long idSupporto, String nomeTeam, String testoRichiesta, String stato) {
        this.idSupporto = idSupporto;
        this.nomeTeam = nomeTeam;
        this.testoRichiesta = testoRichiesta;
        this.stato = stato;
    }

    public Long getIdSupporto() { return idSupporto; }
    public String getNomeTeam() { return nomeTeam; }
    public String getTestoRichiesta() { return testoRichiesta; }
    public String getStato() { return stato; }
}