package uz.app.clothingstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.*;
import org.springframework.data.domain.Pageable;
import uz.app.clothingstore.entity.abs.AbsLongEntity;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.mapper.ProductMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.req.ProductVariantReqDTO;
import uz.app.clothingstore.payload.req.UpdateProductReqDTO;
import uz.app.clothingstore.payload.resp.ProductRespDTO;
import uz.app.clothingstore.payload.resp.ProductVariantRespDTO;
import uz.app.clothingstore.repostory.*;
import uz.app.clothingstore.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductStatisticRepository productStatisticRepository;
    private final FilterParameterItemRepository filterParameterItemRepository;

    @Override
    @Transactional
    public ApiResponse<?> addProduct(ProductReqDTO productReqDTO) {
        Product product = productMapper.toEntity(productReqDTO);

        Category category = categoryRepository.findById(productReqDTO.getCategoryId())
                .orElseThrow(() -> new ItemNotFoundException("Category not found"));
        product.setCategory(category);

        productRepository.save(product);

        if (product.getIsExistVariant()) {
            List<ProductVariantReqDTO> variantsDTO = productReqDTO.getVariants();

            List<ProductVariant> variants = variantsDTO.stream().map(dto -> {
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
    public ApiResponse<?> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productPage = productRepository.findAllByIsDeletedFalseAndIsActiveTrue(pageable);

        List<ProductRespDTO> productDTOs = productPage
                .stream()
                .map(productMapper::toRespDTO)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("products", productDTOs);
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return ApiResponse.success("Product list", response);
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        List<ProductVariant> variants = productVariantRepository
                .findAllByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(product.getId());

        ProductStatistic statistic = productStatisticRepository
                .findProductStatisticByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(product.getId())
                .orElseThrow(() -> new ItemNotFoundException("Product Statistic not found"));

        List<Review> reviews = reviewRepository
                .findAllByProduct_IdAndProduct_IsActiveTrueAndIsDeletedFalse(product.getId());

        reviews.forEach(r -> {
            r.setDeleted(true);
            r.setActive(false);
        });

        variants.forEach(v -> {
            v.setDeleted(true);
            v.setActive(false);
        });

        product.setActive(false);
        product.setDeleted(true);

        statistic.setActive(false);
        statistic.setDeleted(true);

        productRepository.save(product);
        reviewRepository.saveAll(reviews);
        productStatisticRepository.save(statistic);
        productVariantRepository.saveAll(variants);

        return ApiResponse.success("Product deleted successfully", null);
    }

    @Override
    @Transactional
    public ApiResponse<?> updateProduct(UpdateProductReqDTO productReqDTO) {
        Product product = productRepository.findById(productReqDTO.getProductId())
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        Category category = categoryRepository.findById(productReqDTO.getCategoryId())
                .orElseThrow(() -> new ItemNotFoundException("Category not found"));

        product.setName(productReqDTO.getName());
        product.setDescription(productReqDTO.getDescription());
        product.setPrice(productReqDTO.getPrice());
        product.setQuantity(productReqDTO.getQuantity());
        product.setCategory(category);

        productRepository.save(product);

        return ApiResponse.success("Product updated successfully", product);
    }

    @Override
    public ApiResponse<?> getProductById(Long id) {
        Product product = productRepository.findByIdAndIsDeletedFalseAndIsActiveTrue(id)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        ProductRespDTO dto = productMapper.toRespDTO(product);

        if (product.getIsExistVariant()) {
            List<ProductVariantRespDTO> variantRespDTOS = productVariantRepository
                    .findAllByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(product.getId())
                    .stream()
                    .map(v -> {
                        List<Long> list = v.getItems()
                                .stream()
                                .map(AbsLongEntity::getId)
                                .toList();

                        return ProductVariantRespDTO.builder()
                                .id(v.getId())
                                .price(v.getPrice())
                                .quantity(v.getQuantity())
                                .filterItemIds(list)
                                .build();
                    }).toList();

            dto.setVariants(variantRespDTOS);
        }

        return ApiResponse.success("", dto);
    }
}
