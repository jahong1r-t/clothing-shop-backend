package uz.app.clothingstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.FilterParameterItem;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductVariant;
import uz.app.clothingstore.entity.VariantStats;
import uz.app.clothingstore.entity.abs.AbsLongEntity;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.mapper.ProductVariantMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.NewVariantReqDTO;
import uz.app.clothingstore.payload.req.UpdateVariantReqDTO;
import uz.app.clothingstore.payload.resp.ProductVariantRespDTO;
import uz.app.clothingstore.repostory.FilterParameterItemRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.repostory.ProductVariantStatsRepository;
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
    private final ProductVariantStatsRepository productVariantStatsRepository;

    @Override
    @Transactional
    public ApiResponse<?> addProductVariant(NewVariantReqDTO newVariantReqDTO) {
        Product product = productRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(newVariantReqDTO.getProductId())
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        if (!product.getIsExistVariant()) {
            product.setIsExistVariant(true);
            productRepository.save(product);
        }

        List<FilterParameterItem> items = filterParameterItemRepository.findAllById(newVariantReqDTO.getFilterItemIds());
        if (items.size() != newVariantReqDTO.getFilterItemIds().size()) {
            throw new ItemNotFoundException("Some filter items not found for variant");
        }

        ProductVariant variant = productVariantMapper.toEntity(newVariantReqDTO);
        variant.setProduct(product);
        variant.setItems(items);

        productVariantRepository.save(variant);
        productVariantStatsRepository.save(new VariantStats(variant));

        return ApiResponse.success(
                "Product variant created successfully",
                Map.of("id", variant.getId()));
    }

    @Override
    public ApiResponse<?> updateProductVariant(UpdateVariantReqDTO dto) {
        ProductVariant variant = productVariantRepository
                .findByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(dto.getVariantId())
                .orElseThrow(() -> new ItemNotFoundException("Product variant not found"));

        List<FilterParameterItem> items = filterParameterItemRepository.findAllById(dto.getFilterItemIds());
        if (items.size() != dto.getFilterItemIds().size()) {
            throw new ItemNotFoundException("Some filter items not found for variant");
        }

        variant.setItems(items);
        variant.setPrice(dto.getPrice());
        variant.setQuantity(dto.getQuantity());
        productVariantRepository.save(variant);

        return ApiResponse.success("Product variant updated successfully",
                Map.of("id", variant.getId()));
    }

    @Override
    public ApiResponse<?> deleteProductVariant(Long variantId) {
        ProductVariant variant = productVariantRepository.findByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(variantId)
                .orElseThrow(() -> new ItemNotFoundException("Product variant not found"));

        VariantStats stats = productVariantStatsRepository.findByIdAndVariant_IsActiveTrueAndIsDeletedFalse(variantId)
                .orElseThrow(() -> new ItemNotFoundException("Product variant statistic not found"));

        stats.setDeleted(true);
        stats.setActive(false);

        variant.setActive(false);
        variant.setDeleted(true);

        productVariantRepository.save(variant);
        productVariantStatsRepository.save(stats);

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
