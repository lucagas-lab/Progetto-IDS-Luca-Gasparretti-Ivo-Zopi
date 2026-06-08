package unicam.ids.hackhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.service.GestoreUtente;
import unicam.ids.hackhub.service.GestoreLogin;
import unicam.ids.hackhub.security.LoginRequest;
import unicam.ids.hackhub.dto.RegistrazioneUtenteDTO;
import unicam.ids.hackhub.dto.AggiornaProfiloDTO;


@RestController
@RequestMapping("/utenti")
public class UtenteBoundary {

    private final GestoreUtente gestoreUtente;
    private final GestoreLogin gestoreLogin;

    public UtenteBoundary(GestoreUtente gestoreUtente, GestoreLogin gestoreLogin) {
        this.gestoreUtente = gestoreUtente;
        this.gestoreLogin = gestoreLogin;
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

    @PostMapping("/registra") // M maiuscola!
    public ResponseEntity<String> effettuaRegistrazione(@RequestBody RegistrazioneUtenteDTO dto){
        try{
            gestoreUtente.effettuaRegistrazione(
                    dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRuolo()
            );
            return new ResponseEntity<>("Registrazione effettuata con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login") // M maiuscola!
    public ResponseEntity<String> effettuaLogin(@RequestBody LoginRequest request){
        try{
            // Il gestore ci restituisce il VERO token JWT
            String token = gestoreLogin.effettuaLogin(request.getUsername(), request.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenziali errate", HttpStatus.UNAUTHORIZED);
        }
    }
}

