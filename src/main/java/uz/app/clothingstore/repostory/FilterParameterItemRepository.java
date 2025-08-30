package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.FilterParameterItem;
import uz.app.clothingstore.repostory.projection.FilterParameterItemProjection;

@RepositoryRestResource(path = "filter-parameter-item", excerptProjection = FilterParameterItemProjection.class)
public interface FilterParameterItemRepository extends JpaRepository<FilterParameterItem, Long> {
}
