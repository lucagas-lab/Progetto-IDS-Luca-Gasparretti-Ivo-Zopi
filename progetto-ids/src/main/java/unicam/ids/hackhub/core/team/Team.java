package unicam.ids.hackhub.core.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import unicam.ids.hackhub.core.sottomissioni.Sottomissione;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.core.hackathon.Hackathon;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    @Column(unique = true, nullable = false)
    private String nomeTeam;
    @OneToMany
    @JoinColumn(name = "team_id")
    private List<Utente> utenti;
    @ManyToOne
    @JoinColumn(name="hackathon_id")
    private Hackathon hackathon;
    @JsonIgnore
    @OneToOne(mappedBy = "team")
    private Sottomissione sottomissione;


    public Team(){}

    public Team(String nomeTeam, Utente utente){
        this.nomeTeam= nomeTeam;
        this.utenti= new ArrayList<>();
        this.utenti.add(utente);
        this.hackathon= null;
        this.sottomissione= null;
    }
    public Long getTeamId() {
        return teamId;
    }

    public String getNomeTeam() {
        return nomeTeam;
    }

    public void setNomeTeam(String nome) { this.nomeTeam = nomeTeam; }

    public Hackathon getHackathon() {
        return hackathon;
    }

    public void setHackathon(Hackathon hackathon) {
        this.hackathon = hackathon;
    }

    public List<Utente> getUtenti() {
        return utenti;
    }

    public void addUtente(Utente utente) {
        this.utenti.add(utente);
    }

    public void rimuoviUtente(Utente utente) { this.utenti.remove(utente); }

    public Sottomissione getSottomissione() {
        return sottomissione;
    }

    public void setSottomissione(Sottomissione sottomissione) {
        this.sottomissione = sottomissione;
    }
}
