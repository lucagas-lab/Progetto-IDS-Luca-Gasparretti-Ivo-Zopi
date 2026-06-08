package unicam.ids.hackhub.core.hackathon;

public class StatoInIscrizione implements StatoHackathon {

    @Override
    public void cambiaStato(Hackathon hackathon) {
        hackathon.setStato(new StatoInCorso());
        System.out.println("L'Hackathon ora è nello stato: 'In corso'");
    }

    public String getNomeStato(){ return "In iscrizione";}

    @Override
    public void verificaPossibilitaSottomissione() throws Exception {
        throw new Exception("Errore: L'hackathon è in fase di iscrizione, non è ancora possibile inviare progetti.");
    }

    @Override
    public void verificaPossibilitaValutazione() throws Exception {
        throw new Exception("Errore: Impossibile valutare. L'hackathon è ancora in fase di iscrizione, i team non hanno ancora iniziato a lavorare.");
    }
}
