package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.isActive = true " +
            "AND r.isDeleted = false AND r.product.isActive = true AND r.product.isDeleted = false")
    List<Review> findAllActiveByProductId(@Param("productId") Long productId);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.isActive = true " +
            "AND r.isDeleted = false AND r.product.isActive = true AND r.product.isDeleted = false")
    Page<Review> findAllActiveByProductId(@Param("productId") Long productId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.isActive = true AND r.isDeleted = false " +
            "AND r.product.isActive = true AND r.product.isDeleted = false")
    Optional<Review> findActiveById(@Param("id") UUID id);
}

