package uz.app.clothingstore.payload.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmEmailReqDTO {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Confirmation code is required")
    @Pattern(regexp = "\\d{6}", message = "Confirmation code must be 6 digits")
    private String code;
}
