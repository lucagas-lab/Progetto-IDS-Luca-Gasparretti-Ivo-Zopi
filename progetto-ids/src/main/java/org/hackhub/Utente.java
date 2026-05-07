package org.hackhub;
import java.util.List;
import java.util.ArrayList;

public class Utente extends UtenteAutenticato{
    public Utente(){}

    public Utente(Long id, String nome, String cognome, String email, String password){
        super(id, nome, cognome, email, password);
    }

    public void iscriviteam(Team teamDaIscrivere, Hackathon hackathonScelto){
        if(hackathonScelto.getStato() == StatoHackathon.IN_ISCRIZIONE){
            hackathonScelto.getTeamIscritti().add(teamDaIscrivere);
            System.out.println(teamDaIscrivere.getNomeTeam()+ " iscritto all'hackathon " + hackathonScelto.getNome());
        } else {
            System.out.println("Errore Impossibile completare l'iscrizione. L'Hackathon"+ hackathonScelto.getNome() +"è in stato: "+ hackathonScelto.getStato());
        }

    }

    public Team creaTeam(Long id, String nomeTeam){
        Team nuovoTeam= new Team(id, nomeTeam);
        nuovoTeam.getUtenti().add(this);
        System.out.println("L'utente " + getNome() + " ha fondato il team: " + nomeTeam);
        return nuovoTeam;
    }
}
