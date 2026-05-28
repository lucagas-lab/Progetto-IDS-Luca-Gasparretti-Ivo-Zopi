package unicam.ids.hackhub.service;

import unicam.ids.hackhub.hackhub.*;

import java.time.LocalDateTime;

public class GestoreHackathon {

    public GestoreHackathon(){}
    public Hackathon creaHackathon(String nomeHackathon, LocalDateTime dataInizio, LocalDateTime dataFine, Double premio, Giudice giudice, Mentore mentore, Regolamento regolamento){
        Hackathon nuovoHackathon = new Hackathon(nomeHackathon, dataInizio, dataFine, premio, giudice, mentore, regolamento);
        System.out.println("Hackathon"+ nomeHackathon + "creato con successo");
        return nuovoHackathon;
    }

    public void iscriviTeam(Long idTeam, Long idHackathon){}

    public void visualizzaRegolamento(){}

    public void consultaElencoHackathon(){}
}
