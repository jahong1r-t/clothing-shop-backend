package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AddCartItemReqDTO;
import uz.app.clothingstore.payload.req.UpdateCartItemRequest;

import java.util.UUID;

public interface CartService {
    ApiResponse<?> getCart(Long userId);

    ApiResponse<?> addItemToCart(Long userId, AddCartItemReqDTO dto);

    ApiResponse<?> updateCartItem(Long userId, UUID itemId, UpdateCartItemRequest dto);

    ApiResponse<?> removeCartItem(Long userId, UUID itemId);

    ApiResponse<?> clearCart(Long userId);

    ApiResponse<?> getCartItemCount(Long userId);
}

