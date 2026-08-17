package unicam.ids.hackhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.hackhub.dto.NuovaSegnalazioneDTO;
import unicam.ids.hackhub.dto.SegnalazioneDTO;
import unicam.ids.hackhub.service.GestoreSegnalazione;

import java.util.List;

@RestController
@RequestMapping("/segnalazione")
public class SegnalazioneBoundary {


    private final GestoreSegnalazione gestoreSegnalazione;

    public SegnalazioneBoundary(GestoreSegnalazione gestoreSegnalazione) {
        this.gestoreSegnalazione = gestoreSegnalazione;
    }

    @PreAuthorize("hasAuthority('MENTORE')")
    @PostMapping("/invia")
    public ResponseEntity<Object> segnalaViolazione(
            Authentication authentication,
            @RequestBody NuovaSegnalazioneDTO dto) {

        try {
            String usernameMentore = authentication.getName();
            gestoreSegnalazione.segnalaViolazione(usernameMentore, dto.getIdTeamSospettato(), dto.getDescrizioneViolazione());
            return new ResponseEntity<>("Segnalazione registrata con successo.", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}