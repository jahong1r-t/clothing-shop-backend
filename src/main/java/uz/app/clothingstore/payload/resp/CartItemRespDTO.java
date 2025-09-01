package uz.app.clothingstore.payload.resp;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRespDTO {
    private UUID itemId;
    private Long productId;
    private String productName;
    private Long variantId;
    private Integer quantity;
}
