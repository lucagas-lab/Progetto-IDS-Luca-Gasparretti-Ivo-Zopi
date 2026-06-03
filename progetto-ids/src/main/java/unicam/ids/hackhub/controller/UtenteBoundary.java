package unicam.ids.hackhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.service.GestoreUtente;
import unicam.ids.hackhub.dto.RegistraUtenteDTO;
import unicam.ids2526.gal.progetto_hackhub_gal.security.JwtUtil;
import unicam.ids2526.gal.progetto_hackhub_gal.security.LoginRequest;

@RestController
@RequestMapping("/utenti")
public class UtenteBoundary {
    private final GestoreUtente gestoreUtente;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UtenteBoundary(GestoreUtente gestoreUtente, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.gestoreUtente = gestoreUtente;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PutMapping("/profilo")
    public ResponseEntity<String> aggiornaProfilo(Authentication authentication, @RequestBody AggiornaProfiloDTO dto) {
        try {
            // 1. Prendo l'username dell'utente attualmente loggato (dal token JWT)
            String usernameLoggato = authentication.getName();

            // 2. Recupero l'oggetto Utente completo dal database usando il Gestore
            Utente utenteLoggato = gestoreUtente.getUtenteByUsername(usernameLoggato);

            // 3. Passo tutto al metodo gestisciProfilo
            gestoreUtente.gestisciProfilo(utenteLoggato, dto.getUsername(), dto.getEmail());

            return ResponseEntity.ok("Profilo aggiornato con successo!");

        } catch (IllegalStateException | IllegalArgumentException e) {
            // Se c'è un errore (es. email già in uso), lo restituiamo al frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore di sistema");
        }
    }
}

