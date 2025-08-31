package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.VariantStats;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductVariantStatsRepository extends JpaRepository<VariantStats, Long> {
    @Query("SELECT vs FROM VariantStats vs WHERE vs.id = :id AND vs.isActive = true AND vs.isDeleted = false " +
            "AND vs.variant.isActive = true AND vs.variant.isDeleted = false")
    Optional<VariantStats> findActiveById(@Param("id") Long id);

}
