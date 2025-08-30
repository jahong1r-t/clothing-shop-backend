package uz.app.clothingstore.repostory.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.app.clothingstore.entity.Category;

@Projection(name = "categoryProjection", types = Category.class)
public interface CategoryProjection {
    Long getId();
    String getName();
}
