package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Promotion;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Optional<Promotion> findPromotionByProduct_Id(Long productId);
}
