package org.example;
import java.time.LocalDateTime;


public class Organizzatore extends UtenteAutenticato{

    public Organizzatore(){}

    public Organizzatore(Long id, String nome, String cognome, String email, String password){
        super(id, nome, cognome, email, password);
    }

    public void creaHackathon(String nome, LocalDateTime inizio, LocalDateTime fine, Double premio){}
}
