package unicam.ids.hackhub.dto;

public class CreaSottomissioneDTO {
    private String nome;
    private String linkRepository;
    private String descrizione;
    private String usernameAutore;

    public CreaSottomissioneDTO(){}

    public CreaSottomissioneDTO(String linkRepository, String descrizione, String usernameAutore){
        this.linkRepository = linkRepository;
        this.descrizione = descrizione;
        this.usernameAutore = usernameAutore;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getLinkRepository() { return linkRepository; }

    public void setLinkRepository(String linkRepository) { this.linkRepository = linkRepository; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getUsernameAutore() { return usernameAutore; }

    public void setUsernameAutore(String usernameAutore) { this.usernameAutore = usernameAutore; }

}
