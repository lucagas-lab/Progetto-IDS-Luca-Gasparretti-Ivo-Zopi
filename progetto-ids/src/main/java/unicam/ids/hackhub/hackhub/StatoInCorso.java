package unicam.ids.hackhub.hackhub;

public class StatoInCorso implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon context) {
        context.setStato(new StatoInValutazione());
        System.out.println("Temo scaduto per i progetti! L'Hackathon passa alla fase di valutazione");
    }
}
