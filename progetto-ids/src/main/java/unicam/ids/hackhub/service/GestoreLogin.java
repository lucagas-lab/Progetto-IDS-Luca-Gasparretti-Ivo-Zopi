package unicam.ids.hackhub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import unicam.ids.hackhub.security.JwtUtil;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

@Service
@Transactional
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
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username e password sono obbligatori");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Utente utenteLoggato = utenteRep.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato nel sistema"));

        String tokenGenerato = jwtUtil.generateToken(utenteLoggato.getUsername(), utenteLoggato.getRuolo().name());

        return tokenGenerato;    }
}
