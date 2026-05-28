package unicam.ids2526.gal.hackhub.hackhub;

public class StatoInIscrizione implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon context) {
        context.setStato(new StatoInCorso());
        System.out.println("L'Hackathon ora è nello stato: 'In corso'");
    }
}
