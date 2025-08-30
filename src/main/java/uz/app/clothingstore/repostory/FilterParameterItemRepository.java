package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.clothingstore.entity.FilterParameterItem;

public interface FilterParameterItemRepository extends JpaRepository<FilterParameterItem, Long> {
}
