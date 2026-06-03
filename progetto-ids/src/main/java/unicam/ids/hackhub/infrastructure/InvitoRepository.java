package unicam.ids.hackhub.infrastructure;

import unicam.ids.hackhub.core.inviti.EsitoInvito;
import unicam.ids.hackhub.core.inviti.Invito;
import unicam.ids.hackhub.core.utenti.Utente;

import java.util.List;

public interface InvitoRepository {

    List<Invito> findByRicevente(Utente ricevente);

    boolean existsByMittenteAndRiceventeAndEsitoInvitoNot(Utente mittente, Utente ricevente, EsitoInvito esitoInvito);
}
