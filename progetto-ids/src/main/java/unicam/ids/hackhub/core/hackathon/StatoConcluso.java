package unicam.ids.hackhub.core.hackathon;

public class StatoConcluso implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        System.out.println("L'Hackathon è già nel suo stato finale. Impossibile avanzare ulteriormente.");
    }

    public String getNomeStato(){ return "Concluso";}
}

