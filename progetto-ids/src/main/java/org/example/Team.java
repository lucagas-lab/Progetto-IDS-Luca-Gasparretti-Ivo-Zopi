package org.example;
import java.util.List;

public class Team {
    private Long id;
    private String nomeTeam;

    private java.util.List<Utente> utenti= new java.util.ArrayList<>();

    public Team(){}

    public Team(Long id, String nomeTeam){
        this.id= id;
        this.nomeTeam= nomeTeam;
    }
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNomeTeam(){
        return nomeTeam;
    }

    public void setNomeTeam(String nomeTeam){
        this.nomeTeam = nomeTeam;
    }

    public List<Utente> getUtenti(){
        return utenti;
    }

    public void setUtenti(List<Utente> utenti){
        this.utenti = utenti;
    }

}
