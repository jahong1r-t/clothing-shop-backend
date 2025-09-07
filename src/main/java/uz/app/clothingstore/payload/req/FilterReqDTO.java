package uz.app.clothingstore.payload.req;

import lombok.*;
import uz.app.clothingstore.entity.enums.ProductSortType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterReqDTO {

    private Long categoryId;

    @Builder.Default
    private Double minPrice = 0.0;

    @Builder.Default
    private Double maxPrice = Double.MAX_VALUE;

    @Builder.Default
    private List<Long> filterItemIds = new ArrayList<>();

    @Builder.Default
    private ProductSortType sortBy = ProductSortType.NEWEST;

    public void dto() {
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;
        if (filterItemIds == null) filterItemIds = new ArrayList<>();
        if (sortBy == null) sortBy = ProductSortType.NEWEST;
    }
}
