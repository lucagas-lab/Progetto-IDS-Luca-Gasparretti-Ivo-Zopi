package unicam.ids.hackhub.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unicam.ids.hackhub.core.hackathon.*;
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
    private final UtenteRepository utenteRep;
    private final TeamRepository teamRep;

    public GestoreHackathon(HackathonRepository hackathonRep, UtenteRepository utenteRep, TeamRepository teamRep) {
        this.hackathonRep = hackathonRep;
        this.utenteRep = utenteRep;
        this.teamRep = teamRep;
    }

    public void creaHackathon(String nomeHackathon, Double premio, Integer dimensioneTeam, String regolamento, String userOrganizzatore,
                              String userGiudice, List<String> userMentori) throws Exception {

        if (hackathonRep.findByNomeHackathon(nomeHackathon).isPresent() || nomeHackathon.isEmpty()) {
            throw new Exception("Nome per l'hackathon non valido");
        }

        if (premio.isNaN() || premio < 100) {
            throw new Exception("Premio non valido");
        }

        if (dimensioneTeam == null || dimensioneTeam < 1) {
            throw new Exception("Dimensione team non valida");
        }

        if (regolamento == null || regolamento.isEmpty()) {
            System.out.println(regolamento);
            throw new Exception("Regolamento non valido");
        }

        Utente giudice = utenteRep.findByUsername(userGiudice).orElseThrow(
                () -> new Exception("Giudice non esistente")
        );

        if (giudice.getRuolo() != Ruolo.GIUDICE) {
            throw new Exception("Il giudice deve avere il ruolo GIUDICE");
        }

        List<Utente> mentori = new ArrayList<>() {
        };
        for (String user : userMentori) {
            Utente mentore = utenteRep.findByUsername(user).orElseThrow(
                    () -> new Exception("Mentore non esistente"));
            if (mentore.getRuolo() != Ruolo.MENTORE) {
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

    public void iscriviTeam(Long idTeam, Long idHackathon) throws Exception {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: L'Hackathon con l'ID richiesto non esiste: " + idHackathon));
        Team team = teamRep.findById(idTeam)
                .orElseThrow(() -> new Exception("Errore: Il team non esiste: " + idTeam));
        if (team.getHackathon() == hackathon) {
            throw new Exception("Errore: Il team è già iscritto all'Hackathon");
        }
        hackathon.addTeam(team);
        team.setHackathon(hackathon);
        teamRep.save(team);
        hackathonRep.save(hackathon);
    }

    public String visualizzaRegolamento(Long idHackathon) throws Exception {
        Hackathon regolamento = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: L'Hackathon con l'ID richiesto non esiste: " + idHackathon));
        return regolamento.getRegolamento();
    }

    public List<HackathonDTO> consultaElencoHackathon() {
        List<Hackathon> listaHackathon = hackathonRep.findAll();
        return listaHackathon.stream()
                .map(this::convertiDTO)
                .collect(Collectors.toList());
    }

    public HackathonDTO selezionaHackathon(Long idHackathon) throws Exception {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: Nessun Hackathon trovato con ID: " + idHackathon));


        return convertiDTO(hackathon);
    }

    public List<HackathonDTO> consultaHackathonPerStato(String tipoStatoDesiderato) {

        StatoHackathon statoDaCercare;
        switch (tipoStatoDesiderato) {
            case "In corso":
                statoDaCercare = new StatoInCorso();
                break;
            case "In valutazione":
                statoDaCercare = new StatoInValutazione();
                break;
            case "Concluso":
                statoDaCercare = new StatoConcluso();
                break;
            case "In iscrizione":
            default:
                statoDaCercare = new StatoInIscrizione();
                break;
        }

        List<Hackathon> hackathonFiltrati = hackathonRep.findByStato(statoDaCercare);

        return hackathonFiltrati.stream()
                .map(this::convertiDTO)
                .collect(Collectors.toList());
    }

    //METODO PER VISUALIZZARE UN SINGOLO HACKATHON
    public HackathonDTO visualizzaHackathon(Long idHackathon) throws Exception {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: Hackathon non trovato con ID " + idHackathon));

        return convertiDTO(hackathon);
    }

    // METODO PER MODIFICARE UN HACKATHON
    public void modificaHackathon(Long idHackathon, Double nuovoPremio, Integer nuovaDimensione, String nuovoRegolamento) throws Exception {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new Exception("Errore: Hackathon non trovato con ID " + idHackathon));


        if (nuovoPremio != null && nuovoPremio >= 100) {
            hackathon.setPremio(nuovoPremio);
        }
        if (nuovaDimensione != null && nuovaDimensione >= 1) {
            hackathon.setDimenisoneTeam(nuovaDimensione);
        }
        if (nuovoRegolamento != null && !nuovoRegolamento.trim().isEmpty()) {
            hackathon.setRegolamento(nuovoRegolamento);
        }

        hackathonRep.save(hackathon);
    }

    //METODO PER ELIMINARE UN HACKATHON
    public void eliminaHackathon(Long idHackathon) throws Exception {
        if (!hackathonRep.existsById(idHackathon)) {
            throw new Exception("Errore: Impossibile eliminare. Nessun Hackathon trovato con ID " + idHackathon);
        }

        hackathonRep.deleteById(idHackathon);
    }

    public void assegnaMentore(Authentication authentication, Long idHackathon, Long idMentore) {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon non esistente con ID: " + idHackathon));

        if (hackathon.getStato().getNomeStato().equals("CONCLUSO")) {
            throw new IllegalStateException("Impossibile aggiungere mentore: hackathon concluso");
        }

        String usernameLoggato = authentication.getName();
        if (!hackathon.getOrganizzatore().getUsername().equals(usernameLoggato)) {
            throw new IllegalStateException("Errore: Non sei il creatore di questo hackathon.");
        }

        Utente mentore = utenteRep.findById(idMentore)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + idMentore));

        if (mentore.getRuolo() != Ruolo.MENTORE) {
            throw new IllegalArgumentException("Errore: L'utente specificato non ha il ruolo di Mentore.");
        }

        if (hackathon.getMentori().contains(mentore)) {
            throw new IllegalStateException("Mentore già presente in questo hackathon");
        }

        hackathon.getMentori().add(mentore);
        hackathonRep.save(hackathon);
    }

    public void rimuoviMentore(Authentication authentication, Long idHackathon, Long idMentore) {
        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon non esistente con ID: " + idHackathon));

        String usernameLoggato = authentication.getName();
        if (!hackathon.getOrganizzatore().getUsername().equals(usernameLoggato)) {
            throw new IllegalStateException("Errore: Non sei il creatore di questo hackathon.");
        }

        String nomeStato = hackathon.getStato().getNomeStato();
        if ("TERMINATO".equals(nomeStato) || "IN_CORSO".equals(nomeStato)) {
            throw new IllegalStateException("Impossibile rimuovere il mentore: evento in corso o terminato");
        }

        Utente utente = utenteRep.findById(idMentore)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + idMentore));

        if (utente.getRuolo() != Ruolo.MENTORE) {
            throw new IllegalArgumentException("Errore: L'utente specificato non ha il ruolo di Mentore.");
        }

        List<Utente> mentori = hackathon.getMentori();
        if(!mentori.contains(utente)) {
            throw new IllegalArgumentException("Errore: il mentore non fa parte di questo Hackathon.");
        }
        if (mentori.size() <= 1) {
            throw new IllegalStateException("Errore: impossibile rimuovere l'ultimo mentore rimasto");
        }

        hackathon.getMentori().remove(utente);
        hackathonRep.save(hackathon);
    }

    public void cambiaStato(Authentication authentication, Long idHackathon, String nuovoStatoTarget) {

        Hackathon hackathon = hackathonRep.findById(idHackathon)
                .orElseThrow(() -> new IllegalArgumentException("Hackathon non trovato"));

        if (!hackathon.getOrganizzatore().getUsername().equals(authentication.getName())) {
            throw new IllegalStateException("Non sei l'organizzatore di questo evento.");
        }

        String statoAttuale = hackathon.getStato().getNomeStato().toUpperCase();
        String target = nuovoStatoTarget.toUpperCase();

        if (statoAttuale.equals("CONCLUSO")) {
            throw new IllegalStateException("L'Hackathon è già concluso, non puoi più cambiare stato.");
        }
        
        if (statoAttuale.equals("IN ISCRIZIONE") && !target.equals("IN_CORSO")) {
            throw new IllegalStateException("Errore: Da IN_ISCRIZIONE puoi passare solo a IN_CORSO.");
        }

        if (statoAttuale.equals("IN CORSO") && !target.equals("IN_VALUTAZIONE")) {
            throw new IllegalStateException("Errore: Da IN CORSO puoi passare solo a IN_VALUTAZIONE. Non puoi saltare i giudici.");
        }

        if (statoAttuale.equals("IN VALUTAZIONE") && !target.equals("CONCLUSO")) {
            throw new IllegalStateException("Errore: Da IN VALUTAZIONE puoi passare solo a CONCLUSO.");
        }

        switch (target) {
            case "IN_CORSO":
                hackathon.setStato(new StatoInCorso());
                break;
            case "IN_VALUTAZIONE":
                hackathon.setStato(new StatoInValutazione());
                break;
            case "CONCLUSO":
                hackathon.setStato(new StatoConcluso());
                break;
            default:
                throw new IllegalArgumentException("Stato target non riconosciuto.");
        }

        hackathonRep.save(hackathon);
    }

    private HackathonDTO convertiDTO(Hackathon hackathon) {
        String dataInizioStr = (hackathon.getDataInizio() != null)
                ? hackathon.getDataInizio().toString()
                : "Data non definita";

        String statoStr = (hackathon.getStato() != null)
                ? hackathon.getStato().toString()
                : "Sconosciuto";

        List<String> nomiTeam = new ArrayList<>();
        if (hackathon.getTeamPartecipanti() != null) {
            nomiTeam = hackathon.getTeamPartecipanti().stream()
                    .map(Team::getNomeTeam)
                    .collect(Collectors.toList());
        }

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

        return new HackathonDTO(
                hackathon.getNomeHackathon(),
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
