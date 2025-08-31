package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;

public interface StatisticsService {
    ApiResponse<?> getProductStatistic(Long productId);

    ApiResponse<?> getAllProductStatistics(int page, int size);

    ApiResponse<?> getVariantStatistic(Long variantId);

}
