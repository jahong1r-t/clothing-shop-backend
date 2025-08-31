package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.StatisticController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.service.StatisticsService;

@RestController
@RequiredArgsConstructor
public class StatisticControllerImpl implements StatisticController {
    private final StatisticsService statisticsService;

    @Override
    public ResponseEntity<?> getProductStatistic(Long productId) {
        ApiResponse<?> apiResponse = statisticsService.getProductStatistic(productId);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getAllProductStatistics(int page, int size) {
        return null;
    }


    @Override
    public ResponseEntity<?> getVariantStatistic(Long variantId) {
        ApiResponse<?> apiResponse = statisticsService.getVariantStatistic(variantId);
        return ResponseEntity.ok(apiResponse);
    }

}
