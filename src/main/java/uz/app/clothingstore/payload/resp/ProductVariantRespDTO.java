package uz.app.clothingstore.payload.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductVariantRespDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
    private List<Long> filterItemIds;
}
