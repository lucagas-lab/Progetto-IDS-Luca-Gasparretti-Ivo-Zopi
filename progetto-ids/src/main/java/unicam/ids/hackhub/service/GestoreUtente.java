package unicam.ids.hackhub.service;

import unicam.ids.hackhub.core.utenti.UtenteBuilder;
import unicam.ids.hackhub.core.utenti.ConcreteUtenteBuilder;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.UtenteRepository;


public class GestoreUtente {
    private final UtenteRepository utenteRep;
    private final UtenteBuilder utenteBuilder;
    private final PasswordEncoder passwordEncoder;

    public GestoreUtente(UtenteRepository utenteRep, PasswordEncoder passwordEncoder) {
        this.utenteRep = utenteRep;
        this.passwordEncoder = passwordEncoder;
        this.utenteBuilder = new ConcreteUtenteBuilder();
    }


    public Utente getUtenteByUsername(String username) {
        return utenteRep.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato nel database"));
    }

    public void gestisciProfilo(Utente utenteLoggato, String nuovoUsername, String nuovaEmail) {

        if (!utenteLoggato.getUsername().equals(nuovoUsername)) {
            Optional<Utente> utenteEsistente = utenteRep.findByUsername(nuovoUsername);
            if (utenteEsistente.isPresent()) {
                throw new IllegalStateException("Errore: Questo username è già in uso.");
            }
            utenteLoggato.setUsername(nuovoUsername);
        }

        if (!utenteLoggato.getEmail().equals(nuovaEmail)) {
            Optional<Utente> emailEsistente = utenteRep.findByEmail(nuovaEmail);
            if (emailEsistente.isPresent()) {
                throw new IllegalStateException("Errore: Questa email è già registrata.");
            }
            utenteLoggato.setEmail(nuovaEmail);
        }

        utenteRep.save(utenteLoggato);
    }

    public void effettuaRegistrazione(String email, String username, String password, String ruolo) throws Exception{
        if(email == null || !email.contains("@")){
            throw new Exception("Email non valida");
        }
        if(username == null || utenteRep.findByUsername(username).isPresent()){
            throw new Exception("Username non valido o già occupato");
        }
        if(password == null || password.isBlank()){
            throw new Exception("Password non valida");
        }

        ruolo = ruolo.toUpperCase();
        switch(ruolo){
            case "UTENTE", "GIUDICE", "MENTORE", "ORGANIZZATORE" -> {}
            default -> throw new Exception("Ruolo non valido");
        }

        String passwordCriptata = passwordEncoder.encode(password);

        utenteBuilder.resetUtente();
        utenteBuilder.setEmail(email);
        utenteBuilder.setUsername(username);
        utenteBuilder.setPassword(passwordCriptata);
        utenteBuilder.setRuolo(ruolo);

        Utente utente= utenteBuilder.getUtenteFinale();

        utenteRep.save(utente);

    }

}
