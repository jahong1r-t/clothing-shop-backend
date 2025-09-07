package uz.app.clothingstore.payload.resp;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantRespDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;
    private List<Long> filterItemIds;
}
