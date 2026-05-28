package unicam.ids.hackhub.hackhub;


public class StatoInValutazione implements StatoHackathon {

    @Override
    public void avanzaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoConcluso());
        System.out.println("L'hackathon è concluso");
    }

    public String getNomeStato(){ return "In valutazione";}
}
