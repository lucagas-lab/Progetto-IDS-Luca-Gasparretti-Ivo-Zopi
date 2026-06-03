package unicam.ids.hackhub.core.hackathon;

public interface StatoHackathon {

    /** Cambia lo stato dell'Hackathon quando il periodo è terminato **/
    public void cambiaStato(Hackathon h);

    /** Permette di salvare lo stato all'interno del Database nella tabella degli Hackathon **/
    public String getNomeStato();
}
