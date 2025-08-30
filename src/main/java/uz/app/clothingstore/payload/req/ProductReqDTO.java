package uz.app.clothingstore.payload.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductReqDTO {

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

    @NotNull(message = "isExistVariant flag must not be null")
    private Boolean isExistVariant;

    @NotNull(message = "Category ID must not be null")
    private Long categoryId;

    @Valid
    private List<ProductVariantReqDTO> variants;
}
