package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductImage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId AND pi.isMain = true " +
            "AND pi.product.isActive = true AND pi.product.isDeleted = false")
    Optional<ProductImage> findMainActiveProductImage(@Param("productId") Long productId);

    @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id=:productId AND pi.product.isActive = true AND pi.product.isDeleted = false")
    List<ProductImage> findAllProductImageByProductId(@Param("productId") Long productId);
    List<ProductImage> findAllByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(UUID id);
}
