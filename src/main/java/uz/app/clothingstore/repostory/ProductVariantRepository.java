package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductVariant;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findAllByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(Long product_id);

    Optional<ProductVariant> findByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(Long id);

}
