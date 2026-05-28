package unicam.ids.hackhub.hackhub;
import java.time.LocalDateTime;


public class Organizzatore extends UtenteAutenticato {

    public Organizzatore(){}

    public Organizzatore(Long id, String nome, String cognome, String email, String password){
        super(id, nome, cognome, email, password);
    }

    public Hackathon creaHackathon(String nomeHackathon, LocalDateTime inizio, LocalDateTime fine, Double premio, Regolamento regolamento){
        Hackathon nuovoHackathon = new Hackathon(nomeHackathon, inizio, fine, premio, regolamento);
        System.out.println("Hackathon"+ nomeHackathon + "creato con successo");
        return nuovoHackathon;
    }
}
