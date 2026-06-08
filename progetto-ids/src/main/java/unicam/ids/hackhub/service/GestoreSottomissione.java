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

        // 2. Controllo: il team ha già inviato qualcosa?
        if (sottomissioneRep.existsByTeam(t)) {
            throw new Exception("Errore: Sottomissione già esistente per questo team");
        }

        // 3. Verifica l'hackathon
        Hackathon h = t.getHackathon();
        if (h == null) {
            throw new Exception("Errore: Hackathon non trovato");
        }

        // delego allo stato il controllo. Se non si può sottomettere,
        // sarà lo stato stesso a lanciare l'eccezione (es. "L'hackathon è chiuso").
        h.getStato().verificaPossibilitaSottomissione();

        // --- SE IL CODICE ARRIVA QUI, SIGNIFICA CHE LO STATO È "IN_CORSO" ---

        Sottomissione sottomissione = new Sottomissione(t, creaDTO.getNome(), creaDTO.getLinkRepository(), creaDTO.getDescrizione());

        t.setSottomissione(sottomissione);
        sottomissioneRep.save(sottomissione);
        teamRep.save(t);
    }

    public SottomissioneDTO selezionaSottomissione(Long idSottomissione) throws Exception{
        // 1. Recupera la sottomissione dal database usando l'ID fornito gratis da JpaRepository
        Sottomissione sottomissione= sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new Exception("Errore: Nessuna sottomissione trovata con ID: " + idSottomissione));

        // 2. Estrae il voto in totale sicurezza per evitare fastidiosi NullPointerException
        // Se il progetto è stato consegnato ma non ancora valutato, il voto rimarrà null
        Double votoAssegnato = null;
        if(sottomissione.getValutazione() != null){
            votoAssegnato = sottomissione.getValutazione().getVoto();
        }

        // 3. Costruisce il DTO di risposta popolando tutti i campi richiesti per la visualizzazione
        SottomissioneDTO rispostaDTO = new SottomissioneDTO(
                sottomissione.getIdSottomissione(),
                sottomissione.getTeam().getNomeTeam(),
                sottomissione.getNome(),
                sottomissione.getDescrizione(),
                sottomissione.getLinkRepository(),
                votoAssegnato
        );

        return rispostaDTO;
    }

    public void valutaSottomissione(ValutaSottomissioneDTO valutaDTO) throws Exception{
        // 1. Recupero la sottomissione dal database
        Sottomissione sottomissione = sottomissioneRep.findById(valutaDTO.getIdSottomissione())
                .orElseThrow(() -> new Exception("Errore: Nessuna sottomissione trovata con ID: " + valutaDTO.getIdSottomissione()));

        // 2. Recupero l'Hackathon a cui appartiene la sottomissione
        // Assumendo che l'entità Team abbia un collegamento all'Hackathon:
        Hackathon hackathon = sottomissione.getTeam().getHackathon();

        // 3. Verifico se l'hackathon è nello stato corretto ("InValutazione")
        // Se non lo è, questo metodo lancerà l'eccezione che abbiamo appena scritto negli stati
        hackathon.getStato().verificaPossibilitaValutazione();

        // 4. creo la nuova valutazione con il voto fornito dal giudice
        Valutazione nuovaValutazione = new Valutazione();
        nuovaValutazione.setVoto(valutaDTO.getVoto());

        // 5. Salvo la valutazione nel suo repository
        nuovaValutazione = valutazioneRep.save(nuovaValutazione);

        // 6. Collego la valutaziona alla sottomissione e aggiorni il database
        sottomissione.setValutazione(nuovaValutazione);
        sottomissioneRep.save(sottomissione);
    }
}

