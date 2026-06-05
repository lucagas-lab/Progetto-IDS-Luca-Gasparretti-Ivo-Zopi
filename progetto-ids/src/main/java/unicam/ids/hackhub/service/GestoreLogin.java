package unicam.ids.hackhub.service;

import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

public class GestoreLogin {

    private final UtenteRepository utenteRep;

    public GestoreLogin(UtenteRepository utenteRep){
        this.utenteRep = utenteRep;
    }

    public String effettuaLogin(String username, String password){
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Username non valido o vuoto");
        }
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password non fornita");
        }

        Utente utenteLoggato = utenteRep.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Credenziali errate"));

        if(!utenteLoggato.getPassword().equals(password)){
            throw new IllegalArgumentException("Credenziali errate");
        }

        return "ACCESSO CONSENTITO COME: " + utenteLoggato.getRuolo();

    }
}
