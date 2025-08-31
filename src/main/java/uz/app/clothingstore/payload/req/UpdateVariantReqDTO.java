package uz.app.clothingstore.payload.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateVariantReqDTO {
    @NotNull(message = "Product variant id must not null")
    private Long variantId;

    @NotNull(message = "Quantity must not be null")
    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotEmpty(message = "At least one filter item must be selected")
    private List<@NotNull(message = "Filter item ID must not be null") Long> filterItemIds;
}
