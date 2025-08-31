package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;

public interface ProductVariantService {
    ApiResponse<?> addProductVariant();

    ApiResponse<?> updateProductVariant();

    ApiResponse<?> deleteProductVariant();

    ApiResponse<?> getProductVariantsByProductId(Long productId);

    ApiResponse<?> getProductVariantById(Long variantId);
}
