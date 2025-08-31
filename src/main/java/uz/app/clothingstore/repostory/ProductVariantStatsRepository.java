package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.clothingstore.entity.VariantStats;

import java.util.Optional;

public interface ProductVariantStatsRepository extends JpaRepository<VariantStats, Long> {
    Optional<VariantStats> findByIdAndVariant_IsActiveTrueAndIsDeletedFalse(Long variantId);
}
