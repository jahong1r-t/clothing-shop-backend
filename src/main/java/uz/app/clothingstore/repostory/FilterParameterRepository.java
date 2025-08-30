package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.app.clothingstore.entity.FilterParameter;
import uz.app.clothingstore.repostory.projection.FilterParameterProjection;

@RepositoryRestResource(path = "filter-parameter", excerptProjection = FilterParameterProjection.class)
public interface FilterParameterRepository extends JpaRepository<FilterParameter, Long> {
}
