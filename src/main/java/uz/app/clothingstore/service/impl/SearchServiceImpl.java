package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.FilterReqDTO;
import uz.app.clothingstore.repostory.ProductImagesRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductVariantRepository;
import uz.app.clothingstore.service.SearchService;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ProductRepository productRepository;
    private final ProductImagesRepository productImagesRepository;
    private final ProductVariantRepository productVariantRepository;


    @Override
    public ApiResponse<?> searchProductsByName(String query, int page, int size) {
        return null;
    }

    @Override
    public ApiResponse<?> filterProducts(FilterReqDTO filterReqDTO, int page, int size) {
        return null;
    }

    @Override
    public ApiResponse<?> getTopSoldProducts(int page, int size) {
        return null;
    }

    @Override
    public ApiResponse<?> getPopularProducts(int page, int size) {
        return null;
    }

    @Override
    public ApiResponse<?> getNewArrivals(int page, int size) {
        return null;
    }
}
