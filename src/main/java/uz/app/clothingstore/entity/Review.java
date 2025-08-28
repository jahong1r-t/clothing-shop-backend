package uz.app.clothingstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsUUIDEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Review extends AbsUUIDEntity {
    @Column(nullable = false)
    private Integer rating;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch =  FetchType.LAZY)
    private User user;
}
