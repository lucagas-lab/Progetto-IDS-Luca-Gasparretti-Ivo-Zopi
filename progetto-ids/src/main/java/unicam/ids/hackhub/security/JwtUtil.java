package unicam.ids.hackhub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Genera la chiave crittografica all'avvio dell'applicazione
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durata del token: 1 giorno (in millisecondi)
    private final long EXPIRATION_TIME = 86400000;

    // Genera il token includendo username e ruolo
    public String generateToken(String username, String ruolo) {
        return Jwts.builder()
                .setSubject(username)
                .claim("ruolo", ruolo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Estrae le informazioni (Claims) dal token per verificarlo
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
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
