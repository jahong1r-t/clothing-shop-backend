package uz.app.clothingstore.payload.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemReqDTO {
    private Long productId;
    private Long variantId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 1, message = "Quant ity must be at least 1")
    private Integer quantity;
}
