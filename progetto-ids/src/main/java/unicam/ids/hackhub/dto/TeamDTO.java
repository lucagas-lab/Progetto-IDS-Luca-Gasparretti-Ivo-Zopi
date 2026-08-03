package unicam.ids.hackhub.dto;

import java.util.List;


public class TeamDTO {
    private Long idTeam;
    private String nomeTeam;
    private String nomeHackathon;
    private List<String> nomeMembri;

    public TeamDTO(Long idTeam, String nomeTeam, String nomeHackathon, List<String> nomeMembri) {
        this.idTeam = idTeam;
        this.nomeTeam = nomeTeam;
        this.nomeHackathon = nomeHackathon;
        this.nomeMembri = nomeMembri;
    }

    public Long getIdTeam() { return idTeam; }

    public String getNomeTeam() { return nomeTeam; }

    public String getNomeHackathon() { return nomeHackathon; }

    public List<String> getNomeMembri() { return nomeMembri; }

}
