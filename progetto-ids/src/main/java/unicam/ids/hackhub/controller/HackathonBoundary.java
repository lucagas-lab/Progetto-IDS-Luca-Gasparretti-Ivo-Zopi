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
                                                @RequestBody CreaHackathonDTO creaDTO) {

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
        }catch(Exception e){
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
}
