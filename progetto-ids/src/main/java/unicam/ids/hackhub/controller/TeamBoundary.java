package unicam.ids.hackhub.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import unicam.ids.hackhub.dto.HackathonDTO;
import unicam.ids.hackhub.service.GestoreTeam;
import unicam.ids.hackhub.dto.TeamDTO;

import java.util.List;

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

    @GetMapping("/seleziona/{id}")
    public ResponseEntity<Object> selezionaHackathon(@PathVariable("id") Long idTeam) throws  Exception {
        try {
            TeamDTO teamDTO = gestoreTeam.selezionaTeam(idTeam);
            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
