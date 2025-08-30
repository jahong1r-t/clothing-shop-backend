package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.ProductImage;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ProductImagesRepository extends JpaRepository<ProductImage, UUID> {
}
