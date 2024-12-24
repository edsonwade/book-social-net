package code.with.vanilson.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Passowrd  is required")
    @NotBlank(message = "Passowrd  is required")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;
    @Email(message = "Email should be valid")
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    private String email;

}
