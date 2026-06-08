package unicam.ids.hackhub.core.sottomissioni;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import unicam.ids.hackhub.core.team.Team;

@Entity
public class Sottomissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSottomissione;

    @Column(nullable = false)
    private String nome;
    @Column(name= "linkRepository", nullable = false)
    private String linkRepository;
    @Column(nullable = false)
    private String descrizione;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "team_id", nullable = false, unique = true)
    private Team team;
    @OneToOne
    @JoinColumn(name = "valutazione_id")
    private Valutazione valutazione;

    public Sottomissione(){}

    public Sottomissione(Team team, String nome, String linkRepository, String descrizione) {
        this.team = team;
        this.nome = nome;
        this.linkRepository = linkRepository;
        this.descrizione = descrizione;
    }

    public Long getIdSottomissione(){
        return idSottomissione;
    }

    public String getNome() { return nome; }

    public String getLinkRepository() { return linkRepository; }

    public void setLinkRepository(String linkRepository){ this.linkRepository=linkRepository; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione){ this.descrizione=descrizione; }

    public Team getTeam(){ return team; }

    public Valutazione getValutazione(){ return valutazione; }

    public void setValutazione(Valutazione valutazione){ this.valutazione=valutazione; }
}
