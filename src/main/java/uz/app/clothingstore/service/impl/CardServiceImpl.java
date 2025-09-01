package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.Cart;
import uz.app.clothingstore.entity.CartItem;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductVariant;
import uz.app.clothingstore.entity.enums.CartItemStatus;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AddCartItemReqDTO;
import uz.app.clothingstore.payload.resp.CartItemRespDTO;
import uz.app.clothingstore.payload.resp.CartRespDTO;
import uz.app.clothingstore.repostory.CartItemRepository;
import uz.app.clothingstore.repostory.CartRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.service.CartService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CartService {
    private final ProductVariantRepository variantRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public ApiResponse<?> getCart(Long userId) {
        Cart cart = cartRepository.findActiveByIdUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.getAllActiveByCartId(cart.getId());

        List<CartItemRespDTO> items = cartItems.stream()
                .map(i -> CartItemRespDTO.builder()
                        .itemId(i.getId())
                        .productId(i.getProduct().getId())
                        .productName(i.getProduct().getName())
                        .variantId(i.getVariant() != null ? i.getVariant().getId() : null)
                        .quantity(i.getQuantity())
                        .build()
                )
                .toList();

        CartRespDTO response = CartRespDTO.builder()
                .cartId(cart.getId())
                .items(items)
                .build();

        return ApiResponse.success("Cart retrieved successfully", response);
    }

    @Override
    public ApiResponse<?> addItemToCart(Long userId, AddCartItemReqDTO dto) {
        Cart cart = cartRepository.findActiveByIdUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("Cart not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setStatus(CartItemStatus.ACTIVE);
        cartItem.setQuantity(dto.getQuantity());

        if (dto.getVariantId() != null) {
            ProductVariant variant = variantRepository.findActiveById(dto.getVariantId())
                    .orElseThrow(() -> new ItemNotFoundException("Variant not found"));
            cartItem.setVariant(variant);
            cartItem.setProduct(variant.getProduct());
        } else {
            Product product = productRepository.findActiveById(dto.getProductId())
                    .orElseThrow(() -> new ItemNotFoundException("Product not found"));
            cartItem.setProduct(product);
        }

        cartItemRepository.save(cartItem);

        return ApiResponse.success("Item added successfully",
                Map.of("itemId", cartItem.getId()));
    }
}
