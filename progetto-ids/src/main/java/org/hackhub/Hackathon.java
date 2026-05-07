package org.hackhub;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Hackathon {
    private Long id;
    private String nome;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double premio;
    private Regolamento regolamento;
    private StatoHackathon stato;

    private List<Team> teamIscritti= new ArrayList<>();
    private static List<Hackathon> elencoHackathon= new ArrayList<>();

    public Hackathon(){}

    public Hackathon(Long id, String nome, LocalDateTime dataInizio, LocalDateTime dataFine, Double premio, Regolamento regolamento, StatoHackathon stato){
        this.id=id;
        this.nome= nome;
        this.dataInizio=dataInizio;
        this.dataFine=dataFine;
        this.premio=premio;
        this.regolamento=regolamento;
        this.stato=stato;
        elencoHackathon.add(this);
    }

    public static List<Hackathon> getElencoHackathon() {
        return elencoHackathon;
    }

    public static void setElencoHackathon(List<Hackathon> elencoHackathon) {
        Hackathon.elencoHackathon = elencoHackathon;
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

}