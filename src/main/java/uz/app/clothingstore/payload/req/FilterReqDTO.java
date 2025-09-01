package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;
import uz.app.clothingstore.entity.enums.ProductSortType;

import java.util.List;

@Getter
@Setter
public class FilterReqDTO {
    private Long categoryId;
    private String minPrice;
    private String maxPrice;
    private List<Long> filterItemIds;
    private ProductSortType sortBy;
}
