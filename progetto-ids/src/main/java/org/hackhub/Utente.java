package org.hackhub;

public class Utente extends UtenteAutenticato{
    public Utente(){}

    public Utente(Long id, String nome, String cognome, String email, String password){
        super(id, nome, cognome, email, password);
    }

    public void creaTeam(){}

    public void iscriviTeam(){}
}
