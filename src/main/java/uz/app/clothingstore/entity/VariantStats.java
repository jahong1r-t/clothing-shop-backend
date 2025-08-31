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
public class VariantStats extends AbsLongEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", unique = true, nullable = false)
    private ProductVariant variant;

    @Column(nullable = false)
    private Float rating = 0.f;

    @Column(nullable = false)
    private Integer totalReviews = 0;

    @Column(nullable = false)
    private Integer totalSold = 0;

    @Column(nullable = false)
    private Integer totalSoldLastWeek = 0;

    @Column(nullable = false)
    private Integer totalSoldLastMonth = 0;
}

