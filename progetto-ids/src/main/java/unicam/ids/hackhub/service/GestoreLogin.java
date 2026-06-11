package unicam.ids.hackhub.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import unicam.ids.hackhub.security.JwtUtil;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

public class GestoreLogin {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtenteRepository utenteRep;

    public GestoreLogin(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UtenteRepository utenteRep) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.utenteRep = utenteRep;
    }

    public String effettuaLogin(String username, String password) throws Exception{
        // Controllo preliminare sui dati in ingresso
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username e password sono obbligatori");
        }

        // 1. Autenticazione tramite Spring Security.
        // Se le credenziali sono errate, il sistema lancia un'eccezione e interrompe il metodo.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Se l'autenticazione ha successo, recuperiamo l'utente dal database per leggerne il ruolo
        Utente utenteLoggato = utenteRep.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato nel sistema"));

        // 3. Generiamo il token passando sia l'username che il nome del ruolo (convertito in stringa)
        String tokenGenerato = jwtUtil.generateToken(utenteLoggato.getUsername(), utenteLoggato.getRuolo().name());

        // 4. Restituiamo il token al controller Boundary
        return tokenGenerato;    }
}
