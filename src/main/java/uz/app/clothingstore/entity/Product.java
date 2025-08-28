package uz.app.clothingstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsLongEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Product extends AbsLongEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private boolean isDiscounted;

    private Double oldPrice;

    private Double discountPercent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
