package unicam.ids.hackhub.dto;

public class SegnalazioneDTO {

    private Long idSegnalazione;
    private String nomeMentore;
    private String nomeTeamSospettato;
    private String descrizione;

    public SegnalazioneDTO(Long idSegnalazione, String nomeMentore, String nomeTeamSospettato, String descrizione) {
        this.idSegnalazione = idSegnalazione;
        this.nomeMentore = nomeMentore;
        this.nomeTeamSospettato = nomeTeamSospettato;
        this.descrizione = descrizione;
    }

    public Long getIdSegnalazione() { return idSegnalazione; }
    public String getNomeMentore() { return nomeMentore; }
    public String getNomeTeamSospettato() { return nomeTeamSospettato; }
    public String getDescrizione() { return descrizione; }
}