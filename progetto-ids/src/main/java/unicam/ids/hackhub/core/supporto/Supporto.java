package unicam.ids.hackhub.core.supporto;

import jakarta.persistence.*;
import unicam.ids.hackhub.core.team.Team;

@Entity
public class Supporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupporto;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(nullable = false, length = 1000)
    private String testoRichiesta;

    @Column(nullable = false)
    private String stato;

    public Supporto() {}

    public Supporto(Team team, String testoRichiesta, String stato) {
        this.team = team;
        this.testoRichiesta = testoRichiesta;
        this.stato = stato;
    }


    public Long getIdSupporto() { return idSupporto; }
    public void setIdSupporto(Long idSupporto) { this.idSupporto = idSupporto; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public String getTestoRichiesta() { return testoRichiesta; }
    public void setTestoRichiesta(String testoRichiesta) { this.testoRichiesta = testoRichiesta; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}