package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductStatistic;

@RepositoryRestResource(exported = false)
public interface ProductStatisticRepository extends JpaRepository<ProductStatistic, Long> {
}
