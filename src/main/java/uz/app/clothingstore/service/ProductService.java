package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.req.UpdateProductReqDTO;

public interface ProductService {
    ApiResponse<?> addProduct(ProductReqDTO productReqDTO);

    ApiResponse<?> getAllProducts(int page, int size);

    ApiResponse<?> getProductById(Long id);

    ApiResponse<?> deleteProduct(Long productId);

    ApiResponse<?> updateProduct(UpdateProductReqDTO updateProductReqDTO);
}
