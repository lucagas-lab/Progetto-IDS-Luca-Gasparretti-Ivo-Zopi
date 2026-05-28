package unicam.ids2526.gal.hackhub.hackhub;

public class Sottomissione {
    private Long id;
    private Double voto;
    private String linkRepository;
    private String descrizione;

    public Sottomissione(){}

    public Sottomissione(Long id, Double voto, String linkRepository, String descrizione){
        this.id=id;
        this.voto=voto;
        this.linkRepository=linkRepository;
        this.descrizione=descrizione;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public Double getVoto(){
        return voto;
    }

    public void setVoto(Double voto){
        this.voto=voto;
    }

    public String getLinkRepository(){
        return linkRepository;
    }

    public void setLinkRepository(String linkRepository){
        this.linkRepository=linkRepository;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione=descrizione;
    }
}
