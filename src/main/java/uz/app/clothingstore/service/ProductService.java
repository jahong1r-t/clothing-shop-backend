package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;

public interface ProductService {
    ApiResponse<?> addProduct(ProductReqDTO productReqDTO);

    ApiResponse<?> getProducts(int page, int size);

    ApiResponse<?> getProductById(Long id);

    ApiResponse<?> deleteProduct(Long productId);

    ApiResponse<?> updateProduct(Long productId, ProductReqDTO productReqDTO);
}
