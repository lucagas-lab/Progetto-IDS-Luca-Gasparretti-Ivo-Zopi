package org.example;
import java.time.LocalDateTime;

public class Hackathon {
    private Long id;
    private String nome;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double premio;
    private String regolamento;
    private StatoHackathon stato;

    public Hackathon(){}

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public LocalDateTime getDataInizio(){
        return dataInizio;
    }
    public void setDataInizio(LocalDateTime dataInizio){
        this.dataInizio = dataInizio;
    }
    public LocalDateTime getDataFine(){
        return dataFine;
    }

    public void setDataFine(LocalDateTime dataFine){
        this.dataFine = dataFine;
    }

    public Double getPremio(){
        return premio;
    }
    public void setPremio(Double premio){
        this.premio = premio;
    }

    public String getRegolamento(){
        return regolamento;
    }
    public void setRegolamento(String regolamento){
        this.regolamento = regolamento;
    }

    public StatoHackathon getStato(){
        return stato;
    }
    public void setStato(StatoHackathon stato){
        this.stato = stato;
    }
}
