package unicam.ids.hackhub.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import unicam.ids.hackhub.dto.CreaHackathonDTO;
import unicam.ids.hackhub.dto.HackathonDTO;
import unicam.ids.hackhub.dto.ModificaHackathonDTO;
import unicam.ids.hackhub.service.GestoreHackathon;

import java.util.List;

@RestController
@RequestMapping("/hackathon")
public class HackathonBoundary {
    private final GestoreHackathon gestoreHackathon;

    public HackathonBoundary(GestoreHackathon gestoreHackathon) {
        this.gestoreHackathon = gestoreHackathon;
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PostMapping("/crea")
    public ResponseEntity<Object> creaHackathon(Authentication authentication,
                                                @ModelAttribute CreaHackathonDTO creaDTO) {

        String nomeHackhathon = creaDTO.getNomeHackathon();
        Double premio = creaDTO.getPremio();
        Integer dimensioneTeam = creaDTO.getDimensioneTeam();
        String regolamento = creaDTO.getRegolamento();
        String userOrg = authentication.getName();
        String userGiudice = creaDTO.getGiudice();
        List<String> usersMentori = creaDTO.getMentori();

        try {
            gestoreHackathon.creaHackathon(nomeHackhathon, premio, dimensioneTeam, regolamento,
                    userOrg, userGiudice, usersMentori);

            return new ResponseEntity<>("Hackathon creato con successo", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Errore interno di sistema durante la creazione", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seleziona/{id}")
    public ResponseEntity<Object> selezionaHackathon(@PathVariable("id") Long idHackathon) {
        try {
            HackathonDTO hackathonDTO = gestoreHackathon.selezionaHackathon(idHackathon);
            return new ResponseEntity<>(hackathonDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/elencoHackathon")
    public ResponseEntity<Object> elencoHackathon() {
        try {
            List<HackathonDTO> elenco = gestoreHackathon.consultaElencoHackathon();
            if (elenco.isEmpty()) {
                return new ResponseEntity<>("Nessun Hackathon nel sistema", HttpStatus.OK);
            }
            return new ResponseEntity<>(elenco, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/visualizzaRegolamento/{id}")
    public ResponseEntity<Object> visualizzaRegolamento(@PathVariable("id") Long idHackathon) {
        try {
            String regolamento = gestoreHackathon.visualizzaRegolamento(idHackathon);
            return new ResponseEntity<>(regolamento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{idHackathon}/iscrivi/{idTeam}")
    public ResponseEntity<String> iscriviTeam(@PathVariable("idHackathon") Long idHackathon, @PathVariable("idTeam") Long idTeam) {
        try {
            gestoreHackathon.iscriviTeam(idHackathon, idTeam);
            return new ResponseEntity<>("Team iscritto con successo all'Hackathon", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/visualizzaHackathon")
    public ResponseEntity<Object> visualizzaHackathon(@PathVariable("id") Long idHackathon) {
        try {
            HackathonDTO hackathonDTO = gestoreHackathon.visualizzaHackathon(idHackathon);
            return new ResponseEntity<>(hackathonDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PutMapping("/{id}/modifica")
    public ResponseEntity<Object> modificaHackathon(
            @PathVariable("id") Long idHackathon,
            @RequestBody ModificaHackathonDTO dto) {
        try {
            gestoreHackathon.modificaHackathon(
                    idHackathon,
                    dto.getPremio(),
                    dto.getDimensioneTeam(),
                    dto.getRegolamento()
            );
            return new ResponseEntity<>("Hackathon modificato con successo!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')") // Solo chi organizza può eliminare!
    @DeleteMapping("/{id}/elimina")
    public ResponseEntity<Object> eliminaHackathon(@PathVariable("id") Long idHackathon) {
        try {
            gestoreHackathon.eliminaHackathon(idHackathon);
            return new ResponseEntity<>("Hackathon eliminato con successo dal sistema!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PutMapping("/{idHackathon}/assegnaMentori/{idMentore}")
    public ResponseEntity<Object> assegnaMentore(
            Authentication authentication,
            @PathVariable("idHackathon") Long idHackathon,
            @PathVariable("idMentore") Long idMentore
    ) {
        try {
            gestoreHackathon.assegnaMentore(authentication, idHackathon, idMentore);
            return new ResponseEntity<>("Mentore assegnato con successo!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @DeleteMapping("/{idHackathon}/mentori/{idMentore}")
    public ResponseEntity<String> rimuoviMentore(
            Authentication authentication, // <-- Rimosso @PathVariable
            @PathVariable("idHackathon") Long idHackathon, // <-- Passato a Long
            @PathVariable("idMentore") Long idMentore      // <-- Passato a Long
    ) {
        try {
            gestoreHackathon.rimuoviMentore(authentication, idHackathon, idMentore);
            return ResponseEntity.ok("Mentore rimosso con successo");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @PutMapping("/{id}/stato")
    public ResponseEntity<String> cambioStatoHackathon(
            Authentication authentication,
            @PathVariable("id") Long idHackathon,
            @RequestParam String nuovoStato) {

        try {
            gestoreHackathon.cambiaStato(authentication, idHackathon, nuovoStato);
            return ResponseEntity.ok("Stato dell'Hackathon aggiornato a: " + nuovoStato);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}