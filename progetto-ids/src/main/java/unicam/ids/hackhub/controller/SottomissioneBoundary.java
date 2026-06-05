package unicam.ids.hackhub.controller;

import unicam.ids.hackhub.dto.SottomissioneDTO;
import unicam.ids.hackhub.dto.CreaSottomissioneDTO;
import unicam.ids.hackhub.service.GestoreHackathon;
import unicam.ids.hackhub.service.GestoreSottomissione;

import java.util.List;

public class SottomissioneBoundary {
    private final GestoreSottomissione gestoreSottomissione;
    
    public SottomissioneBoundary(GestoreSottomissione gestoreSottomissione) {
        this.gestoreSottomissione = gestoreSottomissione;
    }
}
