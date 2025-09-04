package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class PromotionReqDTO {
    private Long productId;
    private LocalDate endDate;
    private Double newPrice;
}
