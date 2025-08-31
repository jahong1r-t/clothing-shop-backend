package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductStatistic;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Long> {
    @Query("SELECT ps FROM ProductStatistic ps WHERE ps.id = :id AND ps.isActive = true AND ps.isDeleted = false " +
            "AND ps.product.isActive = true AND ps.product.isDeleted = false")
    Optional<ProductStatistic> findActiveById(@Param("id") Long id);

    @Query("SELECT ps FROM ProductStatistic ps WHERE ps.isActive = true AND ps.isDeleted = false " +
            "AND ps.product.isActive = true AND ps.product.isDeleted = false")
    Page<ProductStatistic> findAllActiveStatistics(Pageable pageable);


}
