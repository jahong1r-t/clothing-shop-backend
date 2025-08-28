package uz.app.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsLongEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductStatistic extends AbsLongEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @Column(nullable = false)
    private Float rating = 0.f;

    @Column(nullable = false)
    private Integer totalReviews;

    @Column(nullable = false)
    private Integer totalSold;

    @Column(nullable = false)
    private Integer totalSoldLastWeek;

    @Column(nullable = false)
    private Integer totalSoldLastMonth;
}
