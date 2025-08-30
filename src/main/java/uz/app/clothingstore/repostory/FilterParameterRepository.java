package uz.app.clothingstore.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.clothingstore.entity.FilterParameter;

public interface FilterParameterRepository extends JpaRepository<FilterParameter, Long> {
}
