package unicam.ids.hackhub.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.hackhub.dto.SottomissioneDTO;
import unicam.ids.hackhub.dto.CreaSottomissioneDTO;
import unicam.ids.hackhub.service.GestoreHackathon;
import unicam.ids.hackhub.service.GestoreSottomissione;

import java.util.List;

@RestController
@RequestMapping("/sottomissioni")
public class SottomissioneBoundary {
    private final GestoreSottomissione gestoreSottomissione;

    public SottomissioneBoundary(GestoreSottomissione gestoreSottomissione) {
        this.gestoreSottomissione = gestoreSottomissione;
    }

    @PostMapping("/crea")
    public ResponseEntity<Object> creaSottomissione(@RequestBody CreaSottomissioneDTO creaDTO){
        try{
            gestoreSottomissione.creaSottomissione(creaDTO);
            return new ResponseEntity<>("Sottomissione creata con successo", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/seleziona/{id}")
    public ResponseEntity<Object> selezionaSottomissione(@PathVariable Long idSottomissione){
        try{
            SottomissioneDTO sottomissioneDTO = gestoreSottomissione.selezionaSottomissione(idSottomissione);
            return new ResponseEntity<>(sottomissioneDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
