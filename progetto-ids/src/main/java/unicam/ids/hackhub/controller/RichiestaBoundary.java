package unicam.ids.hackhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import unicam.ids.hackhub.dto.RichiestaDTO;
import unicam.ids.hackhub.service.GestoreRichiesta;

import java.util.List;

@RestController
@RequestMapping("/richieste")
public class RichiestaBoundary {
    private final GestoreRichiesta gestoreRichiesta;

    public RichiestaBoundary(GestoreRichiesta gestoreRichiesta) {
        this.gestoreRichiesta = gestoreRichiesta;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/{id}/richiedi-accesso")
    public ResponseEntity<Object> richiediAccessoAlTeam(Authentication authentication, @PathVariable("id") Long idTeam) {
        String username = authentication.getName();
        try {
            gestoreRichiesta.richiediAccessoAlTeam(username, idTeam);
            return new ResponseEntity<>("Richiesta di accesso inviata con successo al team!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/{idRichiesta}/accetta")
    public ResponseEntity<Object> accettaRichiestaAccessoUtente(@PathVariable("idRichiesta") Long idRichiesta) {
        try {
            // Chiamiamo il metodo del service
            gestoreRichiesta.accettaRichiestaAccessoUtente(idRichiesta);

            return new ResponseEntity<>("Richiesta accettata! L'utente è stato aggiunto al team.", HttpStatus.OK);
        } catch (Exception e) {
            // Cattura tutti gli errori (già accettata, utente già in un team, ecc.)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/team/{idTeam}/pendenti")
    public ResponseEntity<Object> getRichiestePendenti(@PathVariable("idTeam") Long idTeam) {
        try {
            // Otteniamo la lista dei DTO
            List<RichiestaDTO> richieste = gestoreRichiesta.getRichiestePendenti(idTeam);

            // Restituiamo la lista (Spring Boot la trasformerà automaticamente in JSON)
            return new ResponseEntity<>(richieste, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
