package uz.app.clothingstore.repostory.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.app.clothingstore.entity.FilterParameter;

@Projection(name = "filterParameterProjection", types = FilterParameter.class)
public interface FilterParameterProjection {
    Long getId();

    String getName();

    @Value("#{target.category.id}")
    Long getCategoryId();
}