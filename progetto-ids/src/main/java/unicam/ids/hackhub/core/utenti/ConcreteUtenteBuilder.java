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
        try {
            utente.setRuolo(Ruolo.valueOf(ruolo.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ruolo non valido: " + ruolo);
        }
    }

    @Override
    public Utente getUtente() {
        return utente;
    }

    @Override
    public void resetUtente() {
        utente= new Utente();
    }
}
