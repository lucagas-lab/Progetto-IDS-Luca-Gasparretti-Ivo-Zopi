package unicam.ids.hackhub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Genera la chiave crittografica all'avvio dell'applicazione
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    // Durata del token: 1 giorno (in millisecondi)
    private final long EXPIRATION_TIME = 86400000;


    public String generateToken(String username, String ruolo) {
        return Jwts.builder()
                .subject(username) // Invece di setSubject
                .claim("ruolo", ruolo) // Se vuoi aggiungere il ruolo nel payload
                .issuedAt(new Date(System.currentTimeMillis())) // Invece di setIssuedAt
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Invece di setExpiration
                .signWith(key)
                .compact();
    }


    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Controlla se il token è valido e non è scaduto
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
