package uz.app.clothingstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.payload.req.NewVariantReqDTO;
import uz.app.clothingstore.payload.req.UpdateVariantReqDTO;

@RequestMapping("/api/v1/product-variant")
public interface VariantController {

    @PostMapping
    ResponseEntity<?> addProductVariant(@RequestBody @Valid NewVariantReqDTO newVariantReqDTO);

    @PutMapping
    ResponseEntity<?> updateProductVariant(@RequestBody @Valid UpdateVariantReqDTO updateVariantReqDTO);

    @DeleteMapping("/{variantId}")
    ResponseEntity<?> deleteProductVariant(@PathVariable Long variantId);

    @GetMapping("/by-product/{productId}")
    ResponseEntity<?> getProductVariantsByProductId(@PathVariable Long productId);

    @GetMapping("/{variantId}")
    ResponseEntity<?> getProductVariantById(@PathVariable Long variantId);
}

