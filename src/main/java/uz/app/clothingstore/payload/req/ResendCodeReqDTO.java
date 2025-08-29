package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendCodeReqDTO {
    private String email;
}
