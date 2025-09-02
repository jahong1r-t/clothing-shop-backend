package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Cart;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c from Cart c where c.user.id=:id AND c.isActive = true AND c.isDeleted = true")
    Optional<Cart> findActiveByIdUserId(@Param("id") Long id);

}
