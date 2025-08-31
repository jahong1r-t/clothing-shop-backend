package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.ProductController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.req.UpdateProductReqDTO;
import uz.app.clothingstore.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public ResponseEntity<?> getAllProducts(int page, int size) {
        ApiResponse<?> allProducts = productService.getAllProducts(page, size);
        return ResponseEntity.ok(allProducts);
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        ApiResponse<?> productById = productService.getProductById(id);
        return ResponseEntity.ok(productById);
    }

    @Override
    public ResponseEntity<?> addProduct(ProductReqDTO productReqDTO) {
        ApiResponse<?> apiResponse = productService.addProduct(productReqDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        ApiResponse<?> apiResponse = productService.deleteProduct(id);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> updateProduct(UpdateProductReqDTO productReqDTO) {
        return null;
    }
}
