package application.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateUserDto {
    @JsonCreator
    public CreateUserDto(@JsonProperty("username") String username,
                         @JsonProperty("password") String password,
                         @JsonProperty("email") String email,
                         @JsonProperty("role") String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    private String username;
    private String password;
    private String email;
    private String role;
}
