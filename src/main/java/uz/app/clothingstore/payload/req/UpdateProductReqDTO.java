package uz.app.clothingstore.payload.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductReqDTO {
    @NotNull(message = "Product id must not null")
    private Long productId;

    @NotBlank(message = "Product name must not be empty")
    private String name;

    @NotBlank(message = "Product description must not be empty")
    private String description;

    @NotNull(message = "Product price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product price must be greater than 0")
    private Double price;

    @NotNull(message = "Stock must not be null")
    @Min(value = 0, message = "Stock must be 0 or greater")
    private Integer quantity;

    @NotNull(message = "Category ID must not be null")
    private Long categoryId;
}
