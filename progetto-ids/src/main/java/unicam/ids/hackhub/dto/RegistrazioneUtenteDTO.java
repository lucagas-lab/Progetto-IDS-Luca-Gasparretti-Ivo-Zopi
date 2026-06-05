package unicam.ids.hackhub.dto;

public class RegistrazioneUtenteDTO {
    private String username;
    private String email;
    private String password;
    private String ruolo;

    public RegistrazioneUtenteDTO(){}

    public String getUsername(){ return username;}

    public void setUsername(String username){ this.username = username;}

    public String getEmail(){ return email;}

    public void setEmail(String email){ this.email = email;}

    public String getPassword(){ return password;}

    public void setPassword(String password){ this.password = password;}

    public String getRuolo(){ return ruolo;}

    public void setRuolo(String ruolo){ this.ruolo = ruolo;}
}
