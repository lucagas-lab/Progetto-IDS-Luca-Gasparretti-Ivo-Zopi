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
    private final ValutazioneRepository valutazioneRep;

    public GestoreSottomissione(SottomissioneRepository sottomissioneRep,
                                TeamRepository teamRep,
                                HackathonRepository hackathonRep,
                                UtenteRepository utenteRep,
                                ValutazioneRepository valutazioneRep){
        this.sottomissioneRep = sottomissioneRep;
        this.teamRep = teamRep;
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.valutazioneRep = valutazioneRep;
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
        //Recupero la sottomissione dal database
        Sottomissione sottomissione= sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Nessuna sottomissione trovata con ID: " + idSottomissione));
       return convertiDTO(sottomissione);
    }

    public void valutaSottomissione(ValutaSottomissioneDTO valutaDTO) throws Exception{
        //Recupero la sottomissione dal database
        Sottomissione sottomissione = sottomissioneRep.findById(valutaDTO.getIdSottomissione())
                .orElseThrow(() -> new Exception("Errore: Nessuna sottomissione trovata con ID: " + valutaDTO.getIdSottomissione()));

        // Recupero l'Hackathon a cui appartiene la sottomissione
        Hackathon hackathon = sottomissione.getTeam().getHackathon();

        // Verifico se l'hackathon è nello stato corretto ("InValutazione"), se non lo è lancia un'eccezione
        hackathon.getStato().verificaPossibilitaValutazione();

        //creo la nuova valutazione con il voto fornito dal giudice
        Valutazione nuovaValutazione = new Valutazione();
        nuovaValutazione.setVoto(valutaDTO.getVoto());

        //Salvo la valutazione nel suo repository
        nuovaValutazione = valutazioneRep.save(nuovaValutazione);

        // Collego la valutaziona alla sottomissione e aggiorni il database
        sottomissione.setValutazione(nuovaValutazione);
        sottomissioneRep.save(sottomissione);
    }

    public void aggiornaSottomissione(Long idSottomissione, String nuovaDescrizione, String nuovoLink) throws Exception{
        // Cerco la sottomissione nel database
        Sottomissione sottomissione = sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Sottomissione non trovata con ID " + idSottomissione));

        // Aggiorno i campi solo se non sono vuoti o nulli
        if (nuovaDescrizione != null && !nuovaDescrizione.trim().isEmpty()) {
            sottomissione.setDescrizione(nuovaDescrizione);
        }
        if (nuovoLink != null && !nuovoLink.trim().isEmpty()) {
            sottomissione.setLinkRepository(nuovoLink);
        }

        sottomissioneRep.save(sottomissione);
    }


    public String scaricaSottomissione(Long idSottomissione) throws Exception{
        //Cerco la sottomissione
        Sottomissione sottomissione = sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Sottomissione non trovata con ID " + idSottomissione));

        //Restituisco il link della repository
        return "Repository del team: " + sottomissione.getLinkRepository() +
                "\nDescrizione: " + sottomissione.getDescrizione();
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

