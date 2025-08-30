package uz.app.clothingstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.*;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.mapper.ProductMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.req.ProductVariantReqDTO;
import uz.app.clothingstore.repostory.*;
import uz.app.clothingstore.service.ProductService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductStatisticRepository productStatisticRepository;
    private final FilterParameterItemRepository filterParameterItemRepository;

    @Override
    @Transactional
    public ApiResponse<?> createNewProduct(ProductReqDTO productReqDTO) {
        Product product = productMapper.toEntity(productReqDTO);

        Category category = categoryRepository.findById(productReqDTO.getCategoryId())
                .orElseThrow(() -> new ItemNotFoundException("Category not found"));
        product.setCategory(category);

        productRepository.save(product);

        if (product.getIsExistVariant()) {
            List<ProductVariantReqDTO> variantsDTO = productReqDTO.getVariants();

            if (variantsDTO == null || variantsDTO.isEmpty()) {
                throw new IllegalArgumentException("Variants must be provided when isExistVariant is true");
            }

            List<ProductVariant> variants = variantsDTO.stream().map(dto -> {
                if (dto.getFilterItemIds() == null || dto.getFilterItemIds().isEmpty()) {
                    throw new IllegalArgumentException("Filter items must not be empty for each variant");
                }

                List<FilterParameterItem> items = filterParameterItemRepository.findAllById(dto.getFilterItemIds());
                if (items.size() != dto.getFilterItemIds().size()) {
                    throw new ItemNotFoundException("Some filter items not found for variant");
                }

                return ProductVariant.builder()
                        .product(product)
                        .price(dto.getPrice())
                        .quantity(dto.getQuantity())
                        .items(items)
                        .build();
            }).toList();

            productVariantRepository.saveAll(variants);
        }

        productStatisticRepository.save(new ProductStatistic(product));

        return ApiResponse.success("Product created successfully",
                Map.of("id", product.getId()));
    }

    @Override
    public ApiResponse<?> getProductList() {
        return null;
    }

    @Override
    public ApiResponse<?> getProductById(Long id) {
        return null;
    }
}
