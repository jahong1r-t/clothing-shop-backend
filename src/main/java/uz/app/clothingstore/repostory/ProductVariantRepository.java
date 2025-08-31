package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductVariant;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query("SELECT v FROM ProductVariant v WHERE v.product.id = :productId AND v.isActive = true " +
            "AND v.isDeleted = false AND v.product.isActive = true AND v.product.isDeleted = false")
    List<ProductVariant> findAllActiveByProductId(@Param("productId") Long productId);

    @Query("SELECT v FROM ProductVariant v WHERE v.id = :id AND v.isActive = true " +
            "AND v.isDeleted = false AND v.product.isActive = true AND v.product.isDeleted = false")
    Optional<ProductVariant> findActiveById(@Param("id") Long id);
}
