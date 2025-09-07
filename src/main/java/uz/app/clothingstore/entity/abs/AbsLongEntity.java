package uz.app.clothingstore.entity.abs;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    @MappedSuperclass
    @EqualsAndHashCode(callSuper = true)
    public abstract class AbsLongEntity extends Auditable  {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }
