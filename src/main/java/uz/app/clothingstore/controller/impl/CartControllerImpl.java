package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.CartController;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AddCartItemReqDTO;
import uz.app.clothingstore.payload.req.UpdateCartItemRequest;
import uz.app.clothingstore.service.CartService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {
    private final CartService cartService;

    @Override
    public ResponseEntity<?> getCart(User user) {
        ApiResponse<?> cart = cartService.getCart(user.getId());
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<?> addCartItem(User user, AddCartItemReqDTO request) {
        ApiResponse<?> apiResponse = cartService.addItemToCart(user.getId(), request);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> updateCartItem(User user, UUID itemId, UpdateCartItemRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeCartItem(User user, UUID itemId) {
        return null;
    }

    @Override
    public ResponseEntity<?> clearCart(User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCartItemCount(User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> getCartTotalPrice(User user) {
        return null;
    }
}
