package uz.app.clothingstore.payload.resp;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRespDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean isExistVariant;
    private Long categoryId;
    private PromotionRespDTO promotion;
    private List<ProductVariantRespDTO> variants;
}
