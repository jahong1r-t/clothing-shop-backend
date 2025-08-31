package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Review;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct_IdAndProduct_IsActiveTrueAndIsDeletedFalse(Long product_id);
}
