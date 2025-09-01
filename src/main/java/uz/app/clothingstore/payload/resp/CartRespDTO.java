package uz.app.clothingstore.payload.resp;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRespDTO {
    private Long cartId;
    private Long userId;
    private List<CartItemRespDTO> items;
    private Double totalPrice;
    private Integer totalQuantity;
}

