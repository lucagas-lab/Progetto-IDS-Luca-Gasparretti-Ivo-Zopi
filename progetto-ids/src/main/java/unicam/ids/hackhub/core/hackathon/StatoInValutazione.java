package unicam.ids.hackhub.core.hackathon;


public class StatoInValutazione implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoConcluso());
        System.out.println("L'hackathon è concluso");
    }

    public String getNomeStato(){ return "In valutazione";}

    @Override
    public void verificaPossibilitaSottomissione() throws Exception {
        throw new Exception("Errore: Il tempo è scaduto. L'hackathon è già in fase di valutazione.");
    }

    @Override
    public void verificaPossibilitaValutazione() throws Exception {} //Il metodo è volutamente vuoto in quanto in questo statp la valutazione è permessa
}
