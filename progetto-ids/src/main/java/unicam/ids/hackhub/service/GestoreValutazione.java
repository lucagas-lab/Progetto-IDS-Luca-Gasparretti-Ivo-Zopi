package unicam.ids.hackhub.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import unicam.ids.hackhub.core.sottomissioni.Sottomissione;
import unicam.ids.hackhub.core.sottomissioni.Valutazione;
import unicam.ids.hackhub.infrastructure.SottomissioneRepository;
import unicam.ids.hackhub.infrastructure.ValutazioneRepository;


@Service
public class GestoreValutazione {

    private final ValutazioneRepository valutazioneRep;
    private final SottomissioneRepository sottomissioneRep;

    public GestoreValutazione(ValutazioneRepository valutazioneRep, SottomissioneRepository sottomissioneRep) {
        this.valutazioneRep = valutazioneRep;
        this.sottomissioneRep = sottomissioneRep;
    }

    public void valutaSottomissione(Authentication authentication, Long idSottomissione, Double voto, String descrizione) {

        Sottomissione sottomissione = sottomissioneRep.findById(idSottomissione)
                .orElseThrow(() -> new IllegalArgumentException("Sottomissione non trovata con ID: " + idSottomissione));

        if (voto < 0.0 || voto > 30.0) {
            throw new IllegalArgumentException("Errore: Il voto deve essere compreso tra 0 e 30.");
        }

        if (valutazioneRep.existsBySottomissione(sottomissione)) {
            throw new IllegalStateException("Errore: Questa sottomissione è già stata valutata da un mentore.");
        }

        Valutazione nuovaValutazione = new Valutazione(voto, descrizione, sottomissione);
        valutazioneRep.save(nuovaValutazione);
        sottomissione.setValutazione(nuovaValutazione);
        sottomissioneRep.save(sottomissione);
    }
}