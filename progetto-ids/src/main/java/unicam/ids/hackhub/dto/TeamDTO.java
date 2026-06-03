package unicam.ids.hackhub.dto;

import java.util.List;


public class TeamDTO {
    private Long id;
    private String nomeTeam;
    private String nomeHackathon;
    private List<String> nomeMembri;
    private List<String> emailMembri;

    public TeamDTO(Long id, String nomeTeam, String nomeHackathon, List<String> nomeMembri, List<String> emailMembri) {
        this.id = id;
        this.nomeTeam = nomeTeam;
        this.nomeHackathon = nomeHackathon;
        this.nomeMembri = nomeMembri;
        this.emailMembri = emailMembri;
    }

    public Long getId() { return id; }

    public String getNomeTeam() { return nomeTeam; }

    public String getNomeHackathon() { return nomeHackathon; }

    public List<String> getNomeMembri() { return nomeMembri; }

    public List<String> getEmailMembri() { return emailMembri; }
}
