package uz.app.clothingstore.repostory;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.payload.req.FilterReqDTO;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false AND p.isActive = true")
    Page<Product> findAllActive(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDeleted = false AND p.isActive = true")
    Optional<Product> findActiveById(@Param("id") Long id);


    @Query("SELECT DISTINCT p FROM Product p JOIN ProductVariant v ON v.product = p JOIN v.items i WHERE p.category.id = :categoryId " +
            "AND p.isActive = true AND p.isDeleted = false AND p.price BETWEEN :minPrice AND :maxPrice AND i.id IN :filterItemIds")
    Page<Product> getProductsByFilter(@Param("categoryId") Long categoryId,
                                      @Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice,
                                      @Param("filterItemIds") List<Long> filterItemIds,
                                      Pageable pageable);

    Page<Product> findByIsActiveTrueAndIsDeletedFalse(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.isDeleted = false " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Product> searchByName(@Param("query") String query, Pageable pageable);


}
