package unicam.ids.hackhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.hackhub.dto.NuovoSupportoDTO;
import unicam.ids.hackhub.dto.SupportoDTO;
import unicam.ids.hackhub.service.GestoreSupporto;

import java.util.List;

@RestController
@RequestMapping("/supporto")
public class SupportoBoundary {

    private final GestoreSupporto gestoreSupporto;

    public SupportoBoundary(GestoreSupporto gestoreSupporto) {
        this.gestoreSupporto = gestoreSupporto;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/invia")
    public ResponseEntity<Object> inviaRichiestaSupporto(
            Authentication authentication,
            @RequestBody NuovoSupportoDTO dto) {

        try {
            String usernameUtente = authentication.getName();
            gestoreSupporto.inviaRichiestaSupporto(usernameUtente, dto.getTestoRichiesta());
            return new ResponseEntity<>("Richiesta di supporto inviata con successo!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MENTORE')")
    @GetMapping("/hackathon/{idHackathon}/visualizza")
    public ResponseEntity<Object> visualizzaRichiestaSupporto(@PathVariable("idHackathon") Long idHackathon) {

        try {
            List<SupportoDTO> richieste = gestoreSupporto.visualizzaRichiestaSupporto(idHackathon);
            return new ResponseEntity<>(richieste, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}