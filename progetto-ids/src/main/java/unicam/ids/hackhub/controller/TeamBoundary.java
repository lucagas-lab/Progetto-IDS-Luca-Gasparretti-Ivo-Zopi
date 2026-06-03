package unicam.ids.hackhub.controller;


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
            retrun new ResponseEntity<>("Team creato con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
