package unicam.ids.hackhub.core.utenti;

public class ConcreteUtenteBuilder implements UtenteBuilder{
    private Utente utente;

    public ConcreteUtenteBuilder(){
        this.utente= new Utente();
    }

    @Override
    public void setEmail(String email){ utente.setEmail(email); }

    @Override
    public void setUsername(String username) { utente.setUsername(username); }

    @Override
    public void setPassword(String password) {
        utente.setPassword(password);
    }

    @Override
    public void setRuolo(String ruolo) {
        ruolo= ruolo.toUpperCase();
        switch (ruolo){
            case "UTENTE":
                utente.setRuolo(Ruolo.UTENTE);
                break;
            case "MENTORE":
                utente.setRuolo(Ruolo.MENTORE);
                break;
            case "ORGANIZZATORE":
                utente.setRuolo(Ruolo.ORGANIZZATORE);
                break;
            case "GIUDICE":
        }
    }

    @Override
    public Utente getutente() {
        return utente;
    }

    @Override
    public void resetUtente() {
        utente= new Utente();
    }
}
