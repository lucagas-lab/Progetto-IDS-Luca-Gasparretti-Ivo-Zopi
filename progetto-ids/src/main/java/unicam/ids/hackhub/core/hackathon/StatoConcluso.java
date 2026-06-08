package unicam.ids.hackhub.core.hackathon;

public class StatoConcluso implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        System.out.println("L'Hackathon è già nel suo stato finale. Impossibile avanzare ulteriormente.");
    }

    public String getNomeStato(){ return "Concluso";}

    @Override
    public void verificaPossibilitaSottomissione() throws Exception {
        throw new Exception("Errore: L'hackathon è definitivamente concluso.");
    }

    @Override
    public void verificaPossibilitaValutazione() throws Exception {
        throw new Exception("Errore: Impossibile valutare. L'hackathon è già concluso e le valutazioni sono definitive.");
    }
}

