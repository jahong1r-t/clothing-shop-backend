package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemRequest {
    private Long productId;
    private Long variantId;
    private Integer quantity;
}
