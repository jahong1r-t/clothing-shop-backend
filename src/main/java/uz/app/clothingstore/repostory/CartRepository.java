package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Cart;

@RepositoryRestResource(exported = false)
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
