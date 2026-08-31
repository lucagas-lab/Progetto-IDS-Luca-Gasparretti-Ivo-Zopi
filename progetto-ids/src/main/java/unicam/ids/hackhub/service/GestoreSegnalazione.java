package unicam.ids.hackhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.segnalazioni.Segnalazione;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Ruolo;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.dto.SegnalazioneDTO;
import unicam.ids.hackhub.infrastructure.HackathonRepository;
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
    private final HackathonRepository hackathonRep;

    public GestoreSegnalazione(SegnalazioneRepository segnalazioneRep, UtenteRepository utenteRep, TeamRepository teamRep,
                               HackathonRepository hackathonRep) {
        this.segnalazioneRep = segnalazioneRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
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

        if (utente == null || utente.getRuolo() != Ruolo.ORGANIZZATORE) {
            throw new IllegalArgumentException("errore");
        }

        List<Hackathon> hackathons = hackathonRep.findByOrganizzatore(utente);

        if (hackathons.isEmpty()) {
            throw new IllegalStateException("Non gestisce hackathon");
        }

        List<Segnalazione> segnalazioni = segnalazioneRep.findByTeamSospettato_HackathonIn(hackathons);

        return segnalazioni.stream()
                .map(s -> new SegnalazioneDTO(
                        s.getIdSegnalazione(),
                        s.getMentore().getUsername(),
                        s.getTeamSospettato().getNomeTeam(),
                        s.getDescrizione()
                ))
                .toList();
    }
}