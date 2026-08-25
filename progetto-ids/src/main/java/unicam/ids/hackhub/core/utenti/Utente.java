package unicam.ids.hackhub.core.utenti;
import jakarta.persistence.*;
import unicam.ids.hackhub.core.team.Team;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    public Utente(){}

    public Utente(String username, String email, String password, Ruolo ruolo){
        this.username = username;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Long getIdUtente(){ return idUtente; }

    public void setIdUtente(Long idUtente){ this.idUtente = idUtente; }

    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }

    public String getEmail(){ return email; }

    public void setEmail(String email){ this.email= email; }

    public String getPassword(){ return password; }

    public void setPassword(String password){ this.password = password; }

    public Ruolo getRuolo(){ return ruolo; }

    public void setRuolo(Ruolo ruolo){ this.ruolo = ruolo;}

    public Team getTeam(){ return team; }

    public void setTeam(Team team) {
        this.team = team;
    }
}
