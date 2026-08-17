package unicam.ids.hackhub.core.segnalazioni;

import jakarta.persistence.*;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Utente;

@Entity
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSegnalazione;

    @ManyToOne
    @JoinColumn(name = "mentore_id", nullable = false)
    private Utente mentore;

    @ManyToOne
    @JoinColumn(name = "team_sospettato_id", nullable = false)
    private Team teamSospettato;

    @Column(nullable = false, length = 2000)
    private String descrizione;

    public Segnalazione() {}

    public Segnalazione(Utente mentore, Team teamSospettato, String descrizione) {
        this.mentore = mentore;
        this.teamSospettato = teamSospettato;
        this.descrizione = descrizione;
    }

    public Long getIdSegnalazione() { return idSegnalazione; }
    public void setIdSegnalazione(Long idSegnalazione) { this.idSegnalazione = idSegnalazione; }

    public Utente getMentore() { return mentore; }
    public void setMentore(Utente mentore) { this.mentore = mentore; }

    public Team getTeamSospettato() { return teamSospettato; }
    public void setTeamSospettato(Team teamSospettato) { this.teamSospettato = teamSospettato; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
}