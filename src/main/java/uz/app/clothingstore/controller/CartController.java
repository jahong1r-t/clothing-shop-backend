package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.req.AddCartItemReqDTO;
import uz.app.clothingstore.payload.req.UpdateCartItemRequest;

import java.util.UUID;

@RequestMapping("/api/v1/cart")
public interface CartController {
    @GetMapping
    ResponseEntity<?> getCart(@AuthenticationPrincipal User user);

    @PostMapping("/items")
    ResponseEntity<?> addCartItem(@AuthenticationPrincipal User user, @RequestBody AddCartItemReqDTO request);

    @PutMapping("/items/{itemId}")
    ResponseEntity<?> updateCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID itemId,
            @RequestBody UpdateCartItemRequest request
    );

    @DeleteMapping("/items/{itemId}")
    ResponseEntity<?> removeCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable UUID itemId);

    @DeleteMapping("/items")
    ResponseEntity<?> clearCart(@AuthenticationPrincipal User user);

    @GetMapping("/count")
    ResponseEntity<?> getCartItemCount(@AuthenticationPrincipal User user);
}

