package uz.app.clothingstore.repostory.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.app.clothingstore.entity.FilterParameterItem;

@Projection(name = "filterParameterItem", types = FilterParameterItem.class)
public interface FilterParameterItemProjection {
    Long getId();

    String getName();

    @Value("#{target.parameter.id}")
    Long getFilterParameterId();
}
