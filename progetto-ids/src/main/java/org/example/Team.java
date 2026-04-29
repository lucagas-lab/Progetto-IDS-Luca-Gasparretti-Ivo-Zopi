package org.example;

public class Team {
    private Long id;
    private String nome;

    public Team(){}

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
