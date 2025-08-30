package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Review;

@RepositoryRestResource(exported = false)
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
