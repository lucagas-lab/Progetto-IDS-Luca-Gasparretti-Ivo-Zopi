package unicam.ids.hackhub.hackhub;

public class StatoInValutazione implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon context) {
        context.setStato(new StatoConcluso());
        System.out.println("Tutte le valutazioni sono terminate. L'Hackathon è ora CONCLUSO!");
    }
}
