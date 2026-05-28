package unicam.ids2526.gal.hackhub.hackhub;

public class Regolamento {
    private String descrizione;

    public Regolamento(String descrizione){
        this.descrizione = descrizione;
    }

    public Regolamento(){}

    public String getDescrizione(){
        return descrizione;
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }
}
