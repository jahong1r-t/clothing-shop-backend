package uz.app.clothingstore.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsLongEntity;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductVariant extends AbsLongEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "product_variant_items",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "filter_item_id"))
    private List<FilterParameterItem> items;

    @Column(nullable = false)
    private Integer stock;

    private Double price;
}
