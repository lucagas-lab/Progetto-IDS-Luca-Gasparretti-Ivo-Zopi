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

    public void consultaElencoHackathon(){}

    public void visualizzaRegolamento(){}

    public void gestisciProfilo(){}
}
