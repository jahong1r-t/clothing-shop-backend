package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;

public interface ProductService {
    ApiResponse<?> createNewProduct(ProductReqDTO productReqDTO);

    ApiResponse<?> getProductList();

    ApiResponse<?> getProductById(Long id);
}
