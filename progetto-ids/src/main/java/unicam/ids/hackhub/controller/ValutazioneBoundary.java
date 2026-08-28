package unicam.ids.hackhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import unicam.ids.hackhub.dto.ValutaSottomissioneDTO;
import unicam.ids.hackhub.service.GestoreValutazione;

@RestController
@RequestMapping("/valutazioni")
public class ValutazioneBoundary {

    private final GestoreValutazione gestoreValutazione;

    public ValutazioneBoundary(GestoreValutazione gestoreValutazione) {
        this.gestoreValutazione = gestoreValutazione;
    }

    @PreAuthorize("hasAuthority('GIUDICE')")
    @PostMapping("/valuta")
    public ResponseEntity<Object> valutaSottomissione(
            Authentication authentication,
            @RequestBody ValutaSottomissioneDTO dto) {

        try {
            gestoreValutazione.valutaSottomissione(
                    authentication,
                    dto.getIdSottomissione(),
                    dto.getVoto(),
                    dto.getDescrizione()
            );
            return new ResponseEntity<>("Valutazione inserita con successo!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}