package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductStatistic;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.FilterReqDTO;
import uz.app.clothingstore.payload.resp.ProductRespDTO;
import uz.app.clothingstore.payload.resp.PromotionRespDTO;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductStatisticRepository;
import uz.app.clothingstore.repostory.PromotionRepository;
import uz.app.clothingstore.service.SearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final ProductStatisticRepository productStatisticRepository;

    @Override
    public ApiResponse<?> searchProductsByName(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> products = productRepository.searchByName(query, pageable);
        List<ProductRespDTO> dto = products.stream().map(p -> {
            ProductRespDTO respDTO = ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .build();

            if (Boolean.TRUE.equals(p.getIsExistPromotion())) {
                promotionRepository.findPromotionByProduct_Id(p.getId()).ifPresent(promotion ->
                        respDTO.setPromotion(
                                PromotionRespDTO.builder()
                                        .endDate(promotion.getEndDate())
                                        .startDate(promotion.getStartDate())
                                        .discountPercent(promotion.getDiscountPercent())
                                        .productId(p.getId())
                                        .active(promotion.isActive())
                                        .build()
                        )
                );
            }

            return respDTO;
        }).toList();

        return ApiResponse.success("Product", dto);
    }

    @Override
    public ApiResponse<?> filterProducts(FilterReqDTO dto, int page, int size) {
        Sort sort = switch (dto.getSortBy()) {
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "price");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "price");
            case NEWEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case OLDEST -> Sort.by(Sort.Direction.ASC, "createdAt");
            default -> Sort.unsorted();
        };

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productsByFilter = productRepository.getProductsByFilter(
                dto.getCategoryId(), dto.getMinPrice(), dto.getMaxPrice(), dto.getFilterItemIds(), pageable
        );

        List<ProductRespDTO> products = productsByFilter.stream().map(p -> {
            ProductRespDTO respDTO = ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .build();

            if (Boolean.TRUE.equals(p.getIsExistPromotion())) {
                promotionRepository.findPromotionByProduct_Id(p.getId()).ifPresent(promotion ->
                        respDTO.setPromotion(
                                PromotionRespDTO.builder()
                                        .endDate(promotion.getEndDate())
                                        .startDate(promotion.getStartDate())
                                        .discountPercent(promotion.getDiscountPercent())
                                        .productId(p.getId())
                                        .active(promotion.isActive())
                                        .build()
                        )
                );
            }

            return respDTO;
        }).toList();

        return ApiResponse.success("Product list", products);
    }

    @Override
    @Cacheable(value = "top_sold_products", key = "'default'")
    public ApiResponse<?> getTopSoldProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductStatistic> topSoldProducts = productStatisticRepository.findTopSoldProducts(pageable);

        List<ProductRespDTO> dto = topSoldProducts.stream().map(ps -> {
            Product p = ps.getProduct();

            ProductRespDTO respDTO = ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .build();

            if (Boolean.TRUE.equals(p.getIsExistPromotion())) {
                promotionRepository.findPromotionByProduct_Id(p.getId()).ifPresent(promotion ->
                        respDTO.setPromotion(
                                PromotionRespDTO.builder()
                                        .endDate(promotion.getEndDate())
                                        .startDate(promotion.getStartDate())
                                        .discountPercent(promotion.getDiscountPercent())
                                        .productId(p.getId())
                                        .active(promotion.isActive())
                                        .build()
                        )
                );
            }

            return respDTO;
        }).toList();

        return ApiResponse.success("Top sold products", dto);
    }

    @Override
    @Cacheable(value = "new_products", key = "'default'")
    public ApiResponse<?> getNewArrivals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> products = productRepository.findByIsActiveTrueAndIsDeletedFalse(pageable);

        List<ProductRespDTO> dto = products.stream().map(p -> {
            ProductRespDTO respDTO = ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .build();

            if (Boolean.TRUE.equals(p.getIsExistPromotion())) {
                promotionRepository.findPromotionByProduct_Id(p.getId()).ifPresent(promotion ->
                        respDTO.setPromotion(
                                PromotionRespDTO.builder()
                                        .endDate(promotion.getEndDate())
                                        .startDate(promotion.getStartDate())
                                        .discountPercent(promotion.getDiscountPercent())
                                        .productId(p.getId())
                                        .active(promotion.isActive())
                                        .build()
                        )
                );
            }

            return respDTO;
        }).toList();

        return ApiResponse.success("New arrivals", dto);
    }
}
