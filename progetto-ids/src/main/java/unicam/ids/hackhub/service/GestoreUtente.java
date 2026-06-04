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

}
