package unicam.ids.hackhub.core.utenti;

public interface UtenteBuilder {
    public void setEmail(String email);
    public void setUsername(String username);
    public void setPassword(String password);
    public void setRuolo(String ruolo);
    public Utente getutente();
    public void resetUtente();
}