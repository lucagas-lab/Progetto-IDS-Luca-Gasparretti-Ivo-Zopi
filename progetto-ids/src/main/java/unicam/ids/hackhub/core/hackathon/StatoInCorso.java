package unicam.ids.hackhub.core.hackathon;

public class StatoInCorso implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoInValutazione());
        System.out.println("L'Hackathon ora è nello stato: 'In valutazione'");
    }

    public String getNomeStato(){ return "In corso";}
}
