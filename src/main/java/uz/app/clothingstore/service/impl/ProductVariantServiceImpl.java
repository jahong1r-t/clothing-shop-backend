package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.abs.AbsLongEntity;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.resp.ProductVariantRespDTO;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.service.ProductVariantService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    public ApiResponse<?> addProductVariant() {
        return null;
    }

    @Override
    public ApiResponse<?> updateProductVariant() {
        return null;
    }

    @Override
    public ApiResponse<?> deleteProductVariant() {
        return null;
    }

    @Override
    public ApiResponse<?> getProductVariantsByProductId(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        List<ProductVariantRespDTO> variantDTOs = productVariantRepository
                .findAllByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(productId)
                .stream()
                .map(v -> {
                    List<Long> itemIds = v.getItems()
                            .stream()
                            .map(AbsLongEntity::getId)
                            .toList();

                    return ProductVariantRespDTO.builder()
                            .id(v.getId())
                            .price(v.getPrice())
                            .quantity(v.getQuantity())
                            .filterItemIds(itemIds)
                            .build();
                })
                .toList();

        return ApiResponse.success("Product variants list", variantDTOs);
    }


    @Override
    public ApiResponse<?> getProductVariantById(Long variantId) {
        return null;
    }
}
