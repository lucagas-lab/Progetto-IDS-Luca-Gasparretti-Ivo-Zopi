package unicam.ids.hackhub.infrastructure;

import unicam.ids.hackhub.core.richieste.EsitoRichiesta;
import unicam.ids.hackhub.core.richieste.Richiesta;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;

public interface RichiestaRepository {

    List<Richiesta> findByRicevente(Utente ricevente);

    boolean existsByMittenteAndRiceventeAndEsitoRichiestaNot(Utente mittente, Utente ricevente, EsitoRichiesta esitoRichiesta);
}
