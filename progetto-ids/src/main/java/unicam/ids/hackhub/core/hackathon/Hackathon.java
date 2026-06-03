package unicam.ids.hackhub.core.hackathon;

import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.core.team.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Hackathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHackathon;

    @Column(unique = true, nullable = false)
    private String nomeHackathon;

    @Column(nullable = false)
    private StatoHackathon stato;

    @Column(nullable = false)
    private LocalDateTime dataInizioStato;

    @Column(nullable = false)
    private Double premio;

    @Column(nullable = false)
    private int dimenisoneTeam; // Mantengo il refuso "dimenisone" come da diagramma

    @Column(nullable = false)
    private String regolamento;

    @ManyToOne
    @JoinColumn(name = "creatore_id", nullable = false)
    private Utente organizzatore;

    @ManyToMany
    @JoinTable(
            name = "hackathon_mentori", // Nome della tabella intermedia che creerà il DB
            joinColumns = @JoinColumn(name = "hackathon_id"), // Colonna verso l'Hackathon
            inverseJoinColumns = @JoinColumn(name = "mentore_id") // Colonna verso l'Utente (Mentore)
    )
    private List<Utente> mentori;

    @ManyToOne
    @JoinColumn(name="giudice_id", nullable = false)
    private Utente giudice;

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    private List<Team> teamPartecipanti;

    public Hackathon(){}

    public Hackathon(String nomeHackathon, Double premio, int dimenisoneTeam,
                     String regolamento, Utente organizzatore, Utente  giudice, List<Utente> mentore) {
        this.nomeHackathon = nomeHackathon;
        this.premio = premio;
        this.dataInizioStato = LocalDateTime.now();
        this.dimenisoneTeam = dimenisoneTeam;
        this.regolamento = regolamento;
        this.organizzatore = organizzatore;
        this.giudice = giudice;
        this.mentori = mentore;
        this.teamPartecipanti = new ArrayList<Team>();
        this.stato = new InIscrizione();
        this.dataInizioStato = LocalDateTime.now();
    }

    public Long getHackathonId() {
        return idHackathon;
    }

    public String getNomeHackathon() {
        return nomeHackathon;
    }
    public void setNomeHackathon(String nome) {
        this.nomeHackathon = nomeHackathon;
    }

    public String getStato() {
        return stato.getNomeStato();
    }
    void setStato(StatoHackathon stato) {this.stato = stato;}

    public void cambiaStato() {this.stato.cambiaStato(this);}

    public LocalDateTime getDataInizioStato() {
        return dataInizioStato;
    }
    void setDataInizioStato(LocalDateTime dataInizioStato) { this.dataInizioStato= dataInizioStato; }

    public Double getPremio() {
        return premio;
    }

    public int getDimenisoneTeam() {
        return dimenisoneTeam;
    }

    public String getRegolamento(){return regolamento;}
    public void setRegolamento(String regolamento){this.regolamento=regolamento;}

    public Utente getOrganizzatore() {
        return organizzatore;
    }

    public List<Utente> getMentori() {
        return mentori;
    }
    public void addMentore(Utente mentore) { this.mentori.add(mentore);}
    public void removeMentore(Utente mentore) {this.mentori.remove(mentore);}

    public Utente getGiudice() { return giudice; }
    void setGiudice(Utente giudice) { this.giudice= giudice; }

    public List<Team> getTeamPartecipanti() { return teamPartecipanti; }

    public void addTeam(Team team) { this.teamPartecipanti.add(team); }
    public void removeTeam(Team team) {
        if (team != null) {
            this.teamPartecipanti.remove(team);
        }
    }

    public boolean sottomissioniValutate() {
        return this.teamPartecipanti.stream()
                // si filtrano solo i team che hanno effettivamente caricato una sottomissione
                .filter(team -> team.getSottomissione() != null)
                // si verifica che tutte queste sottomissioni abbiano una valutazione non nulla
                .allMatch(team -> team.getSottomissione().getValutazione() != null);
    }
}