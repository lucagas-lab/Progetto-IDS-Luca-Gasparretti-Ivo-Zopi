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
    
    public void consultaElencoHackathon(){
        for(Hackathon hackathon : hackathon) {
            System.out.println(hackathon);
        }
    }

    public void visualizzaRegolamento(Hackathon hackathon, Long id){
        if(hackathon == null){
            System.out.println("Hackathon non trovata");
        }
        else{
                System.out.println("Regolamento hackathon: " + hackathon.getRegolamento());
        }
    }

    public void gestisciProfilo(String nome, String cognome, String email, String password){
        System.out.println("Utente: " + getId());
        System.out.println("Nome: " + getNome() + "\n cognome: " + getCognome());
        System.out.println("Email: " + getEmail());
        System.out.println("Inserire Nuovo nome:");
        setNome(nome);
        System.out.println("Inserire Nuovo cognome:");
        setCognome(cognome);
        System.out.println("Inserire Nuova email:");
        if(!(email.equals(this.email))) setEmail(email);
        else System.out.println("Email precedente identica alla nuova inserita");
        System.out.println("Inserire Nuovo password:");
        setPassword(password);
    }
}
