package unicam.ids.hackhub.dto;

public class SottomissioneDTO {
    private Long idSottomissione;
    private String nomeTeam;
    private String nomeSottomissione;
    private String linkRepository;
    private String descrizione;
    private Double valutazione;

    public SottomissioneDTO() {}


    public SottomissioneDTO(Long idSottomissione, String nometeam,  String nomeSottomissione, String linkRepository, String descrizione, Double valutazione) {
        this.idSottomissione = idSottomissione;
        this.nomeTeam = nometeam;
        this.nomeSottomissione = nomeSottomissione;
        this.linkRepository = linkRepository;
        this.descrizione = descrizione;
        this.valutazione = valutazione;
    }

    public Long getIdSottomissione() { return idSottomissione; }

    public String getNomeTeam() { return nomeTeam; }

    public void setNomeTeam(String nomeTeam) { this.nomeTeam = nomeTeam; }

    public String getNomeSottomissione() { return nomeSottomissione; }

    public void setNomeSottomissione(String nomeSottomissione) { this.nomeSottomissione = nomeSottomissione; }

    public String getLinkRepository() { return linkRepository; }

    public void setLinkRepository(String linkRepository) { this.linkRepository = linkRepository; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Double getValutazione() { return valutazione; }

    public void setValutazione(Double valutazione) { this.valutazione = valutazione; }
}
