package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.VariantReqDTO;

public interface ProductVariantService {
    ApiResponse<?> addProductVariant(VariantReqDTO variantReqDTO);

    ApiResponse<?> updateProductVariant(VariantReqDTO variantReqDTO);

    ApiResponse<?> deleteProductVariant(Long variantId);

    ApiResponse<?> getProductVariantsByProductId(Long productId);

    ApiResponse<?> getProductVariantById(Long variantId);
}
