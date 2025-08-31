package uz.app.clothingstore.repostory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Product;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByIsDeletedFalseAndIsActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndIsDeletedFalseAndIsActiveTrue(Long id);
}
