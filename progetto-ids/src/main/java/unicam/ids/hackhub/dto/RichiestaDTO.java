package unicam.ids.hackhub.dto;

import java.time.LocalDateTime;

public class RichiestaDTO {
    private Long idRichiesta;
    private String usernameRichiedente;
    private LocalDateTime dataRichiesta;

    public RichiestaDTO(Long idRichiesta, String usernameRichiedente, LocalDateTime dataRichiesta) {
        this.idRichiesta = idRichiesta;
        this.usernameRichiedente = usernameRichiedente;
        this.dataRichiesta = dataRichiesta;
    }

    public Long getIdRichiesta() { return idRichiesta; }
    public String getUsernameRichiedente() { return usernameRichiedente; }
    public LocalDateTime getDataRichiesta() { return dataRichiesta; }
}