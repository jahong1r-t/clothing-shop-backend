package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import jakarta.validation.constraints.*;

@Getter
@Setter
public class ProductVariantReqDTO {
    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be 0 or greater")
    private Integer quantity;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotEmpty(message = "At least one filter item must be selected")
    private List<@NotNull(message = "Filter item ID must not be null") Long> filterItemIds;
}
