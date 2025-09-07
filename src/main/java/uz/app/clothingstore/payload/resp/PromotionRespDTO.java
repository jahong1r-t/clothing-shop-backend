package uz.app.clothingstore.payload.resp;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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