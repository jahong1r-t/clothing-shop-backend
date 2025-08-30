package uz.app.clothingstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.payload.req.ProductReqDTO;

@RequestMapping("/api/v1/product")
public interface ProductController {
    @GetMapping
    ResponseEntity<?> getAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductReqDTO productReqDTO);

    @PostMapping("/{id}/images")
    ResponseEntity<?> uploadProductImages(@PathVariable Long id, MultipartFile file);

}
