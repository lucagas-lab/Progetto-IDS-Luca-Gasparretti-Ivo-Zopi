package unicam.ids.hackhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.sottomissioni.Sottomissione;
import unicam.ids.hackhub.core.sottomissioni.Valutazione;
import unicam.ids.hackhub.dto.SottomissioneDTO;
import unicam.ids.hackhub.dto.ValutaSottomissioneDTO;
import unicam.ids.hackhub.dto.CreaSottomissioneDTO;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.hackathon.StatoHackathon;
import unicam.ids.hackhub.core.utenti.Ruolo;
import unicam.ids.hackhub.core.utenti.Utente;
import org.springframework.security.core.Authentication;
import unicam.ids.hackhub.infrastructure.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GestoreSottomissione {
    private final SottomissioneRepository sottomissioneRep;
    private final TeamRepository teamRep;
    private final HackathonRepository hackathonRep;
    private final UtenteRepository utenteRep;

    public GestoreSottomissione(SottomissioneRepository sottomissioneRep,
                                TeamRepository teamRep,
                                HackathonRepository hackathonRep,
                                UtenteRepository utenteRep){
        this.sottomissioneRep = sottomissioneRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
    }


    public void creaSottomissione(CreaSottomissioneDTO creaDTO) throws Exception {
        Team t = teamRep.findByUtentiUsername(creaDTO.getUsernameAutore())
                .orElseThrow(() -> new Exception("Errore: Devi fare parte di un team"));

        // Controllo se il team ha già inviato qualcosa
        if (sottomissioneRep.existsByTeam(t)) {
            throw new Exception("Errore: Sottomissione già esistente per questo team");
        }

        // Verifico l'hackathon
        Hackathon h = t.getHackathon();
        if (h == null) {
            throw new Exception("Errore: Hackathon non trovato");
        }

        // delego allo stato il controllo. Se non si può sottomettere, allo viene lanciata l'eccezione
        h.getStato().verificaPossibilitaSottomissione();

        Sottomissione sottomissione = new Sottomissione(t, creaDTO.getNome(), creaDTO.getLinkRepository(), creaDTO.getDescrizione());

        t.setSottomissione(sottomissione);
        sottomissioneRep.save(sottomissione);
        teamRep.save(t);
    }

    public SottomissioneDTO selezionaSottomissione(Long idSottomissione) throws Exception{
        Sottomissione sottomissione= sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Nessuna sottomissione trovata con ID: " + idSottomissione));
       return convertiDTO(sottomissione);
    }

    public void aggiornaSottomissione(Long idSottomissione, String nuovaDescrizione, String nuovoLink) throws Exception{
        Sottomissione sottomissione = sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Sottomissione non trovata con ID " + idSottomissione));

        if (nuovaDescrizione != null && !nuovaDescrizione.trim().isEmpty()) {
            sottomissione.setDescrizione(nuovaDescrizione);
        }
        if (nuovoLink != null && !nuovoLink.trim().isEmpty()) {
            sottomissione.setLinkRepository(nuovoLink);
        }

        sottomissioneRep.save(sottomissione);
    }


    public String scaricaSottomissione(Authentication authentication, Long idSottomissione) throws Exception {

        Sottomissione sottomissione = sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Sottomissione non trovata con ID " + idSottomissione));

        String usernameLoggato = authentication.getName();

        boolean isUtenteSemplice = authentication.getAuthorities().stream()
                .anyMatch(ruolo -> ruolo.getAuthority().equals("UTENTE"));

        if (isUtenteSemplice) {
            Team teamDellUtente = teamRep.findByUtentiUsername(usernameLoggato)
                    .orElseThrow(() -> new Exception("Errore: Non fai parte di nessun team."));

            if (!teamDellUtente.getTeamId().equals(sottomissione.getTeam().getTeamId())) {
                throw new Exception("Accesso Negato: Non puoi spiare il codice di un team avversario!");
            }
        }

        return "Repository del team: " + sottomissione.getLinkRepository() + "\nDescrizione: " + sottomissione.getDescrizione();
    }

    private SottomissioneDTO convertiDTO(Sottomissione sottomissione) {
        Double votoAssegnato = null;
        if (sottomissione.getValutazione() != null) {
            votoAssegnato = sottomissione.getValutazione().getVoto();
        }

        return new SottomissioneDTO(
                sottomissione.getIdSottomissione(),
                sottomissione.getTeam().getNomeTeam(),
                sottomissione.getNome(),
                sottomissione.getLinkRepository(),
                sottomissione.getDescrizione(),
                votoAssegnato
        );
    }
}

