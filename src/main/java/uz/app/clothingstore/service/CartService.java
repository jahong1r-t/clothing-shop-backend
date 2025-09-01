package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AddCartItemReqDTO;

public interface CartService {
    ApiResponse<?> getCart(Long userId);

    ApiResponse<?> addItemToCart(Long userId, AddCartItemReqDTO dto);
}
