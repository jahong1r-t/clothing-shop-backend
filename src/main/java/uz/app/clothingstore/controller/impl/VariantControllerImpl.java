package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.VariantController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.NewVariantReqDTO;
import uz.app.clothingstore.payload.req.UpdateVariantReqDTO;
import uz.app.clothingstore.service.ProductVariantService;

@RestController
@RequiredArgsConstructor
public class VariantControllerImpl implements VariantController {
    private final ProductVariantService productVariantService;

    @Override
    public ResponseEntity<?> addProductVariant(NewVariantReqDTO newVariantReqDTO) {
        ApiResponse<?> apiResponse = productVariantService.addProductVariant(newVariantReqDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> updateProductVariant(UpdateVariantReqDTO updateVariantReqDTO) {
        ApiResponse<?> apiResponse = productVariantService.updateProductVariant(updateVariantReqDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteProductVariant(Long variantId) {
        ApiResponse<?> apiResponse = productVariantService.deleteProductVariant(variantId);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getProductVariantsByProductId(Long productId) {
        ApiResponse<?> apiResponse = productVariantService.getProductVariantsByProductId(productId);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getProductVariantById(Long variantId) {
        ApiResponse<?> apiResponse = productVariantService.getProductVariantById(variantId);
        return ResponseEntity.ok(apiResponse);
    }
}
