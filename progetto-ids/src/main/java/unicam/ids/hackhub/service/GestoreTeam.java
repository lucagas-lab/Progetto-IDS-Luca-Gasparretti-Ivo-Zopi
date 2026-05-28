package unicam.ids.hackhub.service;

import unicam.ids.hackhub.hackhub.Team;

public class GestoreTeam {

    public GestoreTeam(){}

    public Team creaTeam(Long id, String nomeTeam){
        Team nuovoTeam= new Team(id, nomeTeam);
        nuovoTeam.getUtenti().add(this);
        System.out.println("Team" + nomeTeam + "creato con successo");
        return nuovoTeam;
    }
}
