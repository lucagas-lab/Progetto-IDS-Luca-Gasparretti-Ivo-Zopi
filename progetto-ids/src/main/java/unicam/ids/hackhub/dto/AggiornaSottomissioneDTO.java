package unicam.ids.hackhub.dto;

public class AggiornaSottomissioneDTO {
    private String nuovaDescrizione;
    private String nuovoLinkRepository;

    public AggiornaSottomissioneDTO() {}

    public String getNuovaDescrizione() {
        return nuovaDescrizione;
    }

    public void setNuovaDescrizione(String nuovaDescrizione) {
        this.nuovaDescrizione = nuovaDescrizione;
    }

    public String getNuovoLinkRepository() {
        return nuovoLinkRepository;
    }

    public void setNuovoLinkRepository(String nuovoLinkRepository) {
        this.nuovoLinkRepository = nuovoLinkRepository;
    }
}
