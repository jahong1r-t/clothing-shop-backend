package uz.app.clothingstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.FilterParameterItem;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductVariant;
import uz.app.clothingstore.entity.abs.AbsLongEntity;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.mapper.ProductVariantMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.VariantReqDTO;
import uz.app.clothingstore.payload.resp.ProductVariantRespDTO;
import uz.app.clothingstore.repostory.FilterParameterItemRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.service.ProductVariantService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductVariantRepository productVariantRepository;
    private final FilterParameterItemRepository filterParameterItemRepository;

    @Override
    @Transactional
    public ApiResponse<?> addProductVariant(VariantReqDTO variantReqDTO) {
        Product product = productRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(variantReqDTO.getProductId())
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        if (!product.getIsExistVariant()) {
            product.setIsExistVariant(true);
            productRepository.save(product);
        }

        List<FilterParameterItem> items = filterParameterItemRepository.findAllById(variantReqDTO.getFilterItemIds());
        if (items.size() != variantReqDTO.getFilterItemIds().size()) {
            throw new ItemNotFoundException("Some filter items not found for variant");
        }

        ProductVariant variant = productVariantMapper.toEntity(variantReqDTO);
        variant.setProduct(product);
        variant.setItems(items);

        productVariantRepository.save(variant);

        return ApiResponse.success(
                "Product variant created successfully",
                Map.of("id", variant.getId()));
    }

    @Override
    public ApiResponse<?> updateProductVariant(VariantReqDTO variantReqDTO) {
        return null;
    }

    @Override
    public ApiResponse<?> deleteProductVariant(Long variantId) {
        ProductVariant variant = productVariantRepository.findByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(variantId)
                .orElseThrow(() -> new ItemNotFoundException("Product variant not found"));

        variant.setActive(false);
        variant.setDeleted(true);

        productVariantRepository.save(variant);

        return ApiResponse.success("Product variant deleted successfully", null);
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
        ProductVariant variant = productVariantRepository
                .findByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(variantId)
                .orElseThrow(() -> new ItemNotFoundException("Product variant not found"));

        List<Long> list = variant.getItems()
                .stream()
                .map(AbsLongEntity::getId).toList();

        ProductVariantRespDTO dto = productVariantMapper.toDTO(variant);
        dto.setFilterItemIds(list);

        return ApiResponse.success("Product variant", dto);
    }
}
