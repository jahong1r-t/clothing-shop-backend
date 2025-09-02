package uz.app.clothingstore.payload.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductRespDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean isExistVariant;
    private Long categoryId;
    private List<ProductVariantRespDTO> variants;
}
