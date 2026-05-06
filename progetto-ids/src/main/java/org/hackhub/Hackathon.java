package org.hackhub;
import java.time.LocalDateTime;
import java.util.List;

public class Hackathon {
    private Long id;
    private String nome;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double premio;
    private Regolamento regolamento;
    private StatoHackathon stato;

    private java.util.List<Team> teamIscritti= new java.util.ArrayList<>();
    private java.util.List<Hackathon> hackathon= new java.util.ArrayList<>();

    public Hackathon(){}

    public Hackathon(Long id, String nome, LocalDateTime dataInizio, LocalDateTime dataFine, Double premio, Regolamento regolamento, StatoHackathon stato){
        this.id=id;
        this.nome= nome;
        this.dataInizio=dataInizio;
        this.dataFine=dataFine;
        this.premio=premio;
        this.regolamento=regolamento;
        this.stato=stato;
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

    public Regolamento getRegolamento(){
        return regolamento;
    }
    public void setRegolamento(Regolamento regolamento){
        this.regolamento = regolamento;
    }

    public StatoHackathon getStato(){
        return stato;
    }
    public void setStato(StatoHackathon stato){
        this.stato = stato;
    }

    public List<Team> getTeamIscritti(){
        return teamIscritti;
    }

    public void setTeamIscritti(List<Team> teamIscritti){
        this.teamIscritti = teamIscritti;
    }

    public List<Hackathon> getHackathon(){
        return hackathon;
    }
}
