package unicam.ids.hackhub.hackhub;

public class StatoConcluso implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon hackathon) {
        System.out.println("L'Hackathon è già nel suo stato finale. Impossibile avanzare ulteriormente.");
    }

    public String getNomeStato(){ return "Concluso";}
}

