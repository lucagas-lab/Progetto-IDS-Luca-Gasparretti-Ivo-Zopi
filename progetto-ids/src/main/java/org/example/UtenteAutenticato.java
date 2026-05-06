package org.example;

public abstract class UtenteAutenticato {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;

    public UtenteAutenticato(){}

    public UtenteAutenticato(Long id, String nome, String cognome, String email, String password){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCognome(){
        return cognome;
    }
    public void setCognome(String cognome){
        this.cognome = cognome;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public void consultaElencoHackathon(){}

    public void visualizzaRegolamento(){}

    public void gestisciProfilo(){}
}
