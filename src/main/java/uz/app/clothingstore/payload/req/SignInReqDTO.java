package uz.app.clothingstore.payload.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInReqDTO {
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password must not be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        private String password;
}
