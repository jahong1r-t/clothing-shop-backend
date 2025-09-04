package uz.app.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsLongEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Promotion extends AbsLongEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double oldPrice;

    private Double newPrice;

    private Double discountPercent;

    @Column(nullable = false)
    private boolean active;

    private LocalDate startDate;

    private LocalDate endDate;
}
