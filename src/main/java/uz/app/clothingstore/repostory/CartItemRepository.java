package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    @Query("SELECT i from CartItem i where i.isActive = true AND i.isDeleted = true " +
            "AND i.cart.isDeleted=false AND i.cart.isActive = true")
    Optional<CartItem> findActiveById(UUID uuid);


    @Query("SELECT i FROM CartItem i WHERE i.cart.id = :cartId AND i.status = 'ACTIVE' AND i.isActive = true " +
            "AND i.isDeleted = false AND i.cart.isActive = true AND i.cart.isDeleted = false")
    List<CartItem> getAllActiveByCartId(@Param("cartId") Long cartId);


    Integer countByCart_UserId(Long userId);

    void deleteAllByCartId(Long id);

    Optional<CartItem> findByIdAndCart_UserId(UUID itemId, Long userId);
}
