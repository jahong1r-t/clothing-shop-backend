package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.NewVariantReqDTO;
import uz.app.clothingstore.payload.req.UpdateVariantReqDTO;

public interface ProductVariantService {
    ApiResponse<?> addProductVariant(NewVariantReqDTO newVariantReqDTO);

    ApiResponse<?> updateProductVariant(UpdateVariantReqDTO updateVariantReqDTO);

    ApiResponse<?> deleteProductVariant(Long variantId);

    ApiResponse<?> getProductVariantsByProductId(Long productId);

    ApiResponse<?> getProductVariantById(Long variantId);
}
