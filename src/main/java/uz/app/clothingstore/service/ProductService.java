package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;

public interface ProductService {
    ApiResponse<?> createNewProduct(ProductReqDTO productReqDTO);

    ApiResponse<?> getProductList(int page, int size);

    ApiResponse<?> getProductById(Long id);

    ApiResponse<?>getProductVariantsByProductId(Long productId);
}
