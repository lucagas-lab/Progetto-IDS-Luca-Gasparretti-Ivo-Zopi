package unicam.ids2526.gal.hackhub.hackhub;

public class StatoConcluso implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon context) {
        System.out.println("L'Hackathon è già nel suo stato finale. Impossibile avanzare ulteriormente.");
    }
}

