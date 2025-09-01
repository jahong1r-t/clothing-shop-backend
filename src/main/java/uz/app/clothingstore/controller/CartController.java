package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.payload.req.AddCartItemRequest;
import uz.app.clothingstore.payload.req.UpdateCartItemRequest;

import java.util.UUID;

@RequestMapping("/api/v1/cart")
public interface CartController {
    @GetMapping
    ResponseEntity<?> getCart();

    @PostMapping("/items")
    ResponseEntity<?> addCartItem(@RequestBody AddCartItemRequest request);

    @PutMapping("/items/{itemId}")
    ResponseEntity<?> updateCartItem(
            @PathVariable UUID itemId,
            @RequestBody UpdateCartItemRequest request
    );

    @DeleteMapping("/items/{itemId}")
    ResponseEntity<?> removeCartItem(@PathVariable UUID itemId);

    @DeleteMapping("/items")
    ResponseEntity<?> clearCart();

    @GetMapping("/count")
    ResponseEntity<?> getCartItemCount();

    @GetMapping("/total")
    ResponseEntity<?> getCartTotalPrice();
}

