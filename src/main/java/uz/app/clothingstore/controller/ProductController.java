package uz.app.clothingstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.req.UpdateProductReqDTO;

@RequestMapping("/api/v1/product")
public interface ProductController {
    @GetMapping
    ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    ResponseEntity<?> getProductById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<?> addProduct(@RequestBody @Valid ProductReqDTO productReqDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id);

    @PutMapping
    ResponseEntity<?> updateProduct(@RequestBody @Valid UpdateProductReqDTO productReqDTO);
}
