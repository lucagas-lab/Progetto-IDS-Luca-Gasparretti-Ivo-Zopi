package unicam.ids.hackhub.core.utenti;

public class ConcreteUtenteBuilder implements UtenteBuilder{
    private Utente utente;

    public ConcreteUtenteBuilder(){
        this.utente= new Utente();
    }

    @Override
    public void setEmail(String email){ this.utente.setEmail(email); }

    @Override
    public void setUsername(String username) { this.utente.setUsername(username); }

    @Override
    public void setPassword(String password) {
        this.utente.setPassword(password);
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
        return this.utente;
    }

    @Override
    public void resetUtente() {
        this.utente= new Utente();
    }
}
