package uz.app.clothingstore.entity;

import jakarta.persistence.Entity;
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
public class FilterParameterItem extends AbsLongEntity {
    private String name;

    @ManyToOne
    private FilterParameter parameter;
}
