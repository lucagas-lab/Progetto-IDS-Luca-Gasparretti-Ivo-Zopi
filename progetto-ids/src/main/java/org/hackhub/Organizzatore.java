package org.hackhub;
import java.time.LocalDateTime;


public class Organizzatore extends UtenteAutenticato{

    public Organizzatore(){}

    public Organizzatore(Long id, String nome, String cognome, String email, String password){
        super(id, nome, cognome, email, password);
    }

    public Hackathon creaHackathon(String nomeHackathon, LocalDateTime inizio, LocalDateTime fine, Double premio, String regolamento){
        Hackathon nuovoHackathon = new Hackathon(null, nomeHackathon, inizio, fine, premio, StatoHackathon.IN_ISCRIZIONE);
        System.out.println("Hackathon"+ nomeHackathon + "creato con successo");
        return nuovoHackathon;
    }
}
