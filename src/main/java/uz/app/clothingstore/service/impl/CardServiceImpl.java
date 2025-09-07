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
import uz.app.clothingstore.payload.req.UpdateCartItemRequest;
import uz.app.clothingstore.payload.resp.CartItemRespDTO;
import uz.app.clothingstore.payload.resp.CartRespDTO;
import uz.app.clothingstore.repostory.CartItemRepository;
import uz.app.clothingstore.repostory.CartRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

        Map<String, List<CartItem>> grouped = cartItems.stream()
                .collect(Collectors.groupingBy(i ->
                        i.getProduct().getId() + "-" + (i.getVariant() != null ? i.getVariant().getId() : "null")
                ));

        List<CartItemRespDTO> items = grouped.values().stream()
                .map(list -> {
                    CartItem first = list.get(0);
                    int totalQty = list.stream().mapToInt(CartItem::getQuantity).sum();
                    return CartItemRespDTO.builder()
                            .itemId(first.getId())
                            .productId(first.getProduct().getId())
                            .productName(first.getProduct().getName())
                            .variantId(first.getVariant() != null ? first.getVariant().getId() : null)
                            .quantity(totalQty)
                            .build();
                })
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

        Product product;
        ProductVariant variant = null;

        if (dto.getVariantId() != null) {
            variant = variantRepository.findActiveById(dto.getVariantId())
                    .orElseThrow(() -> new ItemNotFoundException("Variant not found"));
            product = variant.getProduct();
        } else {
            product = productRepository.findActiveById(dto.getProductId())
                    .orElseThrow(() -> new ItemNotFoundException("Product not found"));
        }

        CartItem existingItem = cartItemRepository
                .findByCartIdAndProductIdAndVariantId(cart.getId(), product.getId(),
                        variant != null ? variant.getId() : null)
                .orElse(null);

        int requestedQty = dto.getQuantity();
        int availableQty = (variant != null ? variant.getQuantity() : product.getQuantity());

        if (existingItem != null) {
            int newQty = existingItem.getQuantity() + requestedQty;
            if (newQty > availableQty) {
                return ApiResponse.error("Not enough stock available");
            }
            existingItem.setQuantity(newQty);
            cartItemRepository.save(existingItem);
            return ApiResponse.success("Item quantity updated",
                    Map.of("itemId", existingItem.getId(), "quantity", existingItem.getQuantity()));
        } else {
            if (requestedQty > availableQty) {
                return ApiResponse.error("Not enough stock available");
            }
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setVariant(variant);
            cartItem.setStatus(CartItemStatus.ACTIVE);
            cartItem.setQuantity(requestedQty);
            cartItemRepository.save(cartItem);
            return ApiResponse.success("Item added successfully",
                    Map.of("itemId", cartItem.getId(), "quantity", cartItem.getQuantity()));
        }
    }

    @Override
    public ApiResponse<?> updateCartItem(Long userId, UUID itemId, UpdateCartItemRequest dto) {
        CartItem item = cartItemRepository.findByIdAndCart_UserId(itemId, userId)
                .orElseThrow(() -> new ItemNotFoundException("Cart item not found"));

        item.setQuantity(dto.getQuantity());
        cartItemRepository.save(item);

        return ApiResponse.success("Cart item updated", Map.of("itemId", item.getId()));
    }

    @Override
    public ApiResponse<?> removeCartItem(Long userId, UUID itemId) {
        CartItem item = cartItemRepository.findByIdAndCart_UserId(itemId, userId)
                .orElseThrow(() -> new ItemNotFoundException("Cart item not found"));

        cartItemRepository.delete(item);

        return ApiResponse.success("Cart item removed", null);
    }

    @Override
    public ApiResponse<?> clearCart(Long userId) {
        Cart cart = cartRepository.findActiveByIdUserId(userId)
                .orElseThrow(() -> new ItemNotFoundException("Cart not found"));

        cartItemRepository.deleteAllByCartId(cart.getId());

        return ApiResponse.success("Cart cleared", null);
    }

    @Override
    public ApiResponse<?> getCartItemCount(Long userId) {
        Integer count = cartItemRepository.countByCart_UserId(userId);
        return ApiResponse.success("Cart item count", Map.of("count", count));
    }

}
