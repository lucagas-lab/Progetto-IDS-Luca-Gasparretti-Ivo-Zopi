package unicam.ids.hackhub.core.hackathon;

public class StatoInIscrizione implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoInCorso());
        System.out.println("L'Hackathon ora è nello stato: 'In corso'");
    }

    public String getNomeStato(){ return "In iscrizione";}
}
