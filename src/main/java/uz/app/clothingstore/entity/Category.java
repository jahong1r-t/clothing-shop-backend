package uz.app.clothingstore.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.app.clothingstore.entity.abs.AbsLongEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Category extends AbsLongEntity {
    private String name;
}
