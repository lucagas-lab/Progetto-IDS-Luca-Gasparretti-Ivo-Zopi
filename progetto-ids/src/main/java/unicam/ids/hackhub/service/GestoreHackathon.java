package unicam.ids.hackhub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.hackhub.core.hackathon.Hackathon;
import unicam.ids.hackhub.core.team.Team;
import unicam.ids.hackhub.dto.HackathonDTO;
import unicam.ids.hackhub.core.utenti.Ruolo;
import unicam.ids.hackhub.core.utenti.Utente;
import unicam.ids.hackhub.infrastructure.HackathonRepository;
import unicam.ids.hackhub.infrastructure.TeamRepository;
import unicam.ids.hackhub.infrastructure.UtenteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void consultaElencoHackathon(){
        List<Hackathon> hackathon = hackathonRep.findAll();
    }

    public HackathonDTO selezionaHackathon(Long idHackathon) throws Exception{
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(()-> new Exception("Errore: Nessun Hackathon trovato con ID: " + idHackathon));

        // 2. Prepariamo i dati complessi prima di inserirli nel costruttore

        // Gestione sicura della data (se presente)
        String dataInizioStr = (hackathon.getDataInizio() != null)
                ? hackathon.getDataInizio().toString()
                : "Data non definita";

        // Gestione dello stato (assumendo che getStato() restituisca un enum o un oggetto)
        String statoStr = (hackathon.getStato() != null)
                ? hackathon.getStato().toString()
                : "Sconosciuto";

        // Conversione: Da List<Team> a List<String> (solo i nomi)
        List<String> nomiTeam = new ArrayList<>();
        if (hackathon.getTeamPartecipanti() != null) {
            nomiTeam = hackathon.getTeamPartecipanti().stream()
                    .map(Team::getNomeTeam) // Usa il getter corretto della tua entità Team
                    .collect(Collectors.toList());
        }

        // Conversione: Da List<Utente> a List<String> (nomi ed email dei mentori)
        List<String> nomiMentori = new ArrayList<>();
        List<String> emailMentori = new ArrayList<>();
        if (hackathon.getMentori() != null) {
            nomiMentori = hackathon.getMentori().stream()
                    .map(Utente::getUsername)
                    .collect(Collectors.toList());

            emailMentori = hackathon.getMentori().stream()
                    .map(Utente::getEmail)
                    .collect(Collectors.toList());
        }

        // 3. Costruiamo e restituiamo il DTO finale!
        return new HackathonDTO(
                hackathon.getNomeHackathon(), // o getNome() in base a come l'hai chiamato nell'entità
                hackathon.getPremio(),
                hackathon.getDimenisoneTeam(),
                dataInizioStr,
                statoStr,
                nomiTeam,
                hackathon.getOrganizzatore().getUsername(),
                hackathon.getOrganizzatore().getEmail(),
                hackathon.getGiudice().getEmail(),
                nomiMentori,
                emailMentori
        );
    }
}
