package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.controller.ProductController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {
    private final ProductService productService;

    @Override
    public ResponseEntity<?> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> createNewProduct(ProductReqDTO productReqDTO) {
        ApiResponse<?> apiResponse = productService.addProduct(productReqDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> uploadProductImages(Long id, MultipartFile file) {
        return null;
    }
}
