package unicam.ids.hackhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.dto.HackathonDTO;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.core.utenti.Ruolo;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.HackathonRepository;
import unicam.ids.hackhub.infrastructure.TeamRepository;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class GestoreHackathon {

    private final HackathonRepository hackathonRep;
    private final UtenteRepository  utenteRep;
    private final TeamRepository teamRep;

    public GestoreHackathon(HackathonRepository hackathonRep, UtenteRepository utenteRep, TeamRepository teamRep) {
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    public void creaHackathon(String nomeHackathon, Double premio, Integer dimensioneTeam, String regolamento, String userOrganizzatore,
                              String userGiudice, List<String> userMentori) throws Exception{

        if(hackathonRep.findByNome(nomeHackathon).isPresent() || nomeHackathon.isEmpty()){
            throw new Exception("Nome per l'hackathon non valido");
        }

        if(premio.isNaN() || premio < 100){
            throw new Exception("Premio non valido");
        }

        if(dimensioneTeam == null || dimensioneTeam<1){
            throw new Exception("Dimensione team non valida");
        }

        if(regolamento == null||regolamento.isEmpty()){
            System.out.println(regolamento);
            throw new Exception("Regolamento non valido");
        }

        Utente giudice = utenteRep.findByUsername(userGiudice).orElseThrow(
                () -> new Exception("Giudice non esistente")
        );

        if(giudice.getRuolo()!= Ruolo.GIUDICE){
            throw new Exception("Il giudice deve avere il ruolo GIUDICE");
        }

        List<Utente> mentori= new ArrayList<>() {
        };
        for(String user: userMentori){
            Utente mentore=utenteRep.findByUsername(user).orElseThrow(
                    () -> new Exception("Mentore non esistente"));
            if(mentore.getRuolo()!=Ruolo.MENTORE){
                throw new Exception("Il mentore deve avere il ruolo MENTORE");
            }
            mentori.add(mentore);
        }

        Utente organizzatore = utenteRep.findByUsername(userOrganizzatore).orElseThrow(
                () -> new IllegalArgumentException("Organizzatore non esistente")
        );

        Hackathon hackathon = new Hackathon(nomeHackathon, premio, dimensioneTeam, regolamento, organizzatore, giudice, mentori);

        hackathonRep.save(hackathon);

    }

    public void iscriviTeam(Long idTeam, Long idHackathon){}

    public void visualizzaRegolamento(){}

    public void consultaElencoHackathon(){}
}
