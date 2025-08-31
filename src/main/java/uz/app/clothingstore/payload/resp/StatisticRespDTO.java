package uz.app.clothingstore.payload.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticRespDTO {
    private Long id;
    private Integer quantity;
    private Float rating = 0.f;
    private Integer totalReviews = 0;
    private Integer totalSold = 0;
    private Integer totalSoldLastWeek = 0;
    private Integer totalSoldLastMonth = 0;
}
