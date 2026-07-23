package unicam.ids.hackhub.core.hackathon;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatoHackathonConverter implements AttributeConverter<StatoHackathon, String> {

    @Override
    public String convertToDatabaseColumn(StatoHackathon statoHackathon) {
        if (statoHackathon == null) {
            return new StatoInIscrizione().getNomeStato();
        }
        return statoHackathon.getNomeStato();
    }

    @Override
    public StatoHackathon convertToEntityAttribute(String nomeStatoDb){
        if(nomeStatoDb == null){
            return new StatoInIscrizione();
        }
        switch (nomeStatoDb){
            case "In iscrizione": return new StatoInIscrizione();
            case "In corso": return new StatoInCorso();
            case "In valutazione": return new StatoInValutazione();
            case "Concluso": return new StatoConcluso();
            default: return new StatoInIscrizione();
        }
    }

}
