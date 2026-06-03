package unicam.ids.hackhub.dto;

public class AggiornaProfiloDTO {
    private String username;
    private String email;

    public AggiornaProfiloDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
