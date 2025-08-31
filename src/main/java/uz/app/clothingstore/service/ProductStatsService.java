package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;

public interface ProductStatsService {
    ApiResponse<?> getProductStatistic(Long productId);

    ApiResponse<?> getAllProductStatistics();

    ApiResponse<?> getVariantStatistic(Long variantId);

    ApiResponse<?> getProductVariantStatistics(Long productId);

    ApiResponse<?> getAllVariantStatistics();
}
