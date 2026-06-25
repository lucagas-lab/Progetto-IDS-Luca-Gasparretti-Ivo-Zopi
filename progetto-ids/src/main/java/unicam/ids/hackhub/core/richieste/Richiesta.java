package unicam.ids.hackhub.core.richieste;

//import jakarta.persistence.*;

import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

import java.time.LocalDateTime;

@Entity
public class Richiesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRichiesta;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Enumerated(EnumType.STRING) // Salva l'enum come stringa (es. "PENDENTE") nel DB, non come numero
    @Column(nullable = false)
    private EsitoRichiesta statoRichiesta;

    @Column(nullable = false)
    private LocalDateTime dataRichiesta;

    // 1. Costruttore vuoto obbligatorio per Spring/Hibernate
    public Richiesta() {}

    // 2. Costruttore che useremo noi nel GestoreTeam
    public Richiesta(Utente utente, Team team) {
        this.utente = utente;
        this.team = team;
        this.statoRichiesta = EsitoRichiesta.PENDENTE; // Quando nasce, la richiesta è sempre pendente
        this.dataRichiesta = LocalDateTime.now();
    }

    public Long getIdRichiesta() {
        return idRichiesta;
    }

    public Utente getUtente() {
        return utente;
    }

    public Team getTeam() {
        return team;
    }

    public EsitoRichiesta getStato() {
        return statoRichiesta;
    }

    public void setStato(EsitoRichiesta stato) {
        this.statoRichiesta = stato;
    }

    public LocalDateTime getDataRichiesta() {
        return dataRichiesta;
    }
}