package uz.app.clothingstore.payload.resp;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PromotionRespDTO {
    private Long productId;

    private Double newPrice;

    private Double oldPrice;

    private Double discountPercent;

    @Column(nullable = false)
    private boolean active;

    private LocalDate startDate;

    private LocalDate endDate;
}