package unicam.ids.hackhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.segnalazioni.Segnalazione;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Ruolo;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.dto.SegnalazioneDTO;
import unicam.ids.hackhub.infrastructure.SegnalazioneRepository;
import unicam.ids.hackhub.infrastructure.UtenteRepository;
import unicam.ids.hackhub.infrastructure.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GestoreSegnalazione {

    private final SegnalazioneRepository segnalazioneRep;
    private final UtenteRepository utenteRep;
    private final TeamRepository teamRep;

    public GestoreSegnalazione(SegnalazioneRepository segnalazioneRep, UtenteRepository utenteRep, TeamRepository teamRep) {
        this.segnalazioneRep = segnalazioneRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    public void segnalaViolazione(String usernameMentore, Long idTeamSospettato, String descrizione) throws Exception {

        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new Exception("Errore: La descrizione della violazione non può essere vuota.");
        }

        Utente mentore = utenteRep.findByUsername(usernameMentore)
                .orElseThrow(() -> new Exception("Errore: Mentore non trovato."));

        Team teamSospettato = teamRep.findById(idTeamSospettato)
                .orElseThrow(() -> new Exception("Errore: Team sospettato non trovato."));

        if (teamSospettato.getHackathon() == null) {
            throw new Exception("Errore: Il team indicato non partecipa a nessun hackathon.");
        }

        Segnalazione segnalazione = new Segnalazione(mentore, teamSospettato, descrizione);
        segnalazioneRep.save(segnalazione);
    }


    public List<SegnalazioneDTO> visualizzaSegnalazione(String username) {
        Utente utente = utenteRep.findByUsername(username).orElse(null);

        // Primo blocco 'break': controlla se l'utente esiste ed è un ORGANIZZATORE
        if (utente == null || utente.getRuolo() != Ruolo.ORGANIZZATORE) {
            throw new IllegalArgumentException("errore");
        }

        // 2. Recupera gli hackathon gestiti dall'organizzatore
        List<Hackathon> hackathons = hackathonRep.findByOrganizzatore(utente);

        // Secondo blocco 'break': controlla se la lista di hackathon è vuota
        if (hackathons.isEmpty()) {
            throw new IllegalStateException("Non gestisce hackathon");
        }

        // 3. Recupera le segnalazioni associate agli hackathon trovati
        List<Segnalazione> segnalazioni = segnalazioneRep.findByHackathonIn(hackathons);

        // 4. Converte le entità Segnalazione in SegnalazioneDTO e restituisce la lista
        return segnalazioni.stream()
                .map(SegnalazioneDTO::new) // O il tuo metodo/mapper di conversione DTO
                .toList();
    }
}