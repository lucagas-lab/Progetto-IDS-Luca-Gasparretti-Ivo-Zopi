package unicam.ids.hackhub.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import unicam.ids.hackhub.dto.ModificaTeamDTO;
import unicam.ids.hackhub.service.GestoreTeam;
import unicam.ids.hackhub.dto.TeamDTO;

@RestController
@RequestMapping("/team")
public class TeamBoundary {
    private final GestoreTeam gestoreTeam;

    public TeamBoundary(GestoreTeam gestoreTeam) {
        this.gestoreTeam = gestoreTeam;
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/crea")
    public ResponseEntity<Object> creaTeam(Authentication authentication, @RequestBody String nomeTeam){
        String username= authentication.getName();
        try{
            gestoreTeam.creaTeam(username, nomeTeam);
            return new ResponseEntity<>("Team creato con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/seleziona/{id}")
    public ResponseEntity<Object> selezionaHackathon(@PathVariable("id") Long idTeam) throws  Exception {
        try {
            TeamDTO teamDTO = gestoreTeam.selezionaTeam(idTeam);
            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @GetMapping("/{id}/visualizza")
    public ResponseEntity<Object> visualizzaTeam(@PathVariable("id") Long idTeam) {
        try {
            TeamDTO teamDTO = gestoreTeam.visualizzaTeam(idTeam);
            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PutMapping("/{id}/modifica")
    public ResponseEntity<Object> modificaTeam(
            @PathVariable("id") Long idTeam,
            @RequestBody ModificaTeamDTO dto) {
        try {
            gestoreTeam.modificaTeam(idTeam, dto.getNuovoNomeTeam());
            return new ResponseEntity<>("Nome del Team aggiornato con successo!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @PostMapping("/{id}/abbandona")
    public ResponseEntity<Object> abbandonaTeam(
            @PathVariable("id") Long idTeam,
            Authentication authentication) {
        try {
            String username= authentication.getName();
            gestoreTeam.abbandonaTeam(idTeam, username);
            return new ResponseEntity<>("Team abbandonato", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('UTENTE')")
    @DeleteMapping("/{id}/elimina")
    public ResponseEntity<Object> eliminaTeam(@PathVariable("id") Long IdTeam) {
        try {
            gestoreTeam.eliminaTeam(IdTeam);
            return new ResponseEntity<>("Team eliminato", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}