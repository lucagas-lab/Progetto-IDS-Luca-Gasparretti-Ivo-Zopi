package unicam.ids.hackhub.core.hackathon;


public class StatoInValutazione implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoConcluso());
        System.out.println("L'hackathon è concluso");
    }

    public String getNomeStato(){ return "In valutazione";}
}
