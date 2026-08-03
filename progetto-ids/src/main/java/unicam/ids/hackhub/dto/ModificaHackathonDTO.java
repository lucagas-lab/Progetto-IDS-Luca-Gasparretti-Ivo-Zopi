package unicam.ids.hackhub.dto;

public class ModificaHackathonDTO {
    private Double premio;
    private Integer dimensioneTeam;
    private String regolamento;

    public ModificaHackathonDTO() {}

    public Double getPremio() { return premio; }
    public void setPremio(Double premio) { this.premio = premio; }

    public Integer getDimensioneTeam() { return dimensioneTeam; }
    public void setDimensioneTeam(Integer dimensioneTeam) { this.dimensioneTeam = dimensioneTeam; }

    public String getRegolamento() { return regolamento; }
    public void setRegolamento(String regolamento) { this.regolamento = regolamento; }
}