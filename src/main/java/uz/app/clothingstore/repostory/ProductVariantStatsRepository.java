package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.clothingstore.entity.VariantStats;

public interface ProductVariantStatsRepository extends JpaRepository<VariantStats, Long> {
}
