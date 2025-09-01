package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;
import uz.app.clothingstore.entity.enums.ProductSortType;

import java.util.List;

@Getter
@Setter
public class FilterReqDTO {
    private Long categoryId;
    private Double minPrice;
    private Double maxPrice;
    private List<Long> filterItemIds;
    private ProductSortType sortBy;
}
