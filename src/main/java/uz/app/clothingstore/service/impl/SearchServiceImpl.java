package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductImage;
import uz.app.clothingstore.entity.ProductStatistic;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.FilterReqDTO;
import uz.app.clothingstore.payload.resp.ProductRespDTO;
import uz.app.clothingstore.repostory.ProductImagesRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductStatisticRepository;
import uz.app.clothingstore.service.SearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ProductRepository productRepository;
    private final ProductImagesRepository productImagesRepository;
    private final ProductStatisticRepository productStatisticRepository;

    @Override
    public ApiResponse<?> searchProductsByName(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> products = productRepository.searchByName(query, pageable);
        List<ProductRespDTO> dto = products.stream().map(p -> {
            ProductImage img = productImagesRepository.findProductImageByProduct_IdAndIsMainTrue(p.getId())
                    .orElseThrow(() -> new ItemNotFoundException("Product img not found"));

            return ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .imageUrl(img.getS3key())
                    .build();
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
            ProductImage img = productImagesRepository.findProductImageByProduct_IdAndIsMainTrue(p.getId())
                    .orElseThrow(() -> new ItemNotFoundException("Product img not found"));

            return ProductRespDTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .categoryId(p.getCategory().getId())
                    .description(p.getDescription())
                    .isExistVariant(p.getIsExistVariant())
                    .imageUrl(img.getS3key())
                    .build();
        }).toList();

        return ApiResponse.success("Product list", products);
    }

    @Override
    @Cacheable(value = "top_sold_products", key = "'default'")
    public ApiResponse<?> getTopSoldProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductStatistic> topSoldProducts = productStatisticRepository.findTopSoldProducts(pageable);

        List<ProductRespDTO> dto = topSoldProducts.stream().map(ps -> {
            ProductImage img = productImagesRepository.findProductImageByProduct_IdAndIsMainTrue(ps.getProduct().getId())
                    .orElseThrow(() -> new ItemNotFoundException("Product img not found"));

            return ProductRespDTO.builder()
                    .id(ps.getProduct().getId())
                    .name(ps.getProduct().getName())
                    .price(ps.getProduct().getPrice())
                    .quantity(ps.getProduct().getQuantity())
                    .categoryId(ps.getProduct().getCategory().getId())
                    .description(ps.getProduct().getDescription())
                    .isExistVariant(ps.getProduct().getIsExistVariant())
                    .imageUrl(img.getS3key())
                    .build();
        }).toList();

        return ApiResponse.success("Top sold products", dto);
    }

    @Override
    @Cacheable(value = "new_products", key = "'default'")
    public ApiResponse<?> getNewArrivals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> products = productRepository.findByIsActiveTrueAndIsDeletedFalse(pageable);

        return ApiResponse.success("New arrivals", products);
    }
}
