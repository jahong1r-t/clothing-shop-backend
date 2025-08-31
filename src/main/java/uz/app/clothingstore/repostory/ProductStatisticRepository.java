package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductStatistic;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Long> {
    Optional<ProductStatistic> findProductStatisticByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(Long id);
}
