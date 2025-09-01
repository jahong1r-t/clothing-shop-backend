package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.FilterReqDTO;

public interface SearchService {

    ApiResponse<?> searchProductsByName(String query, int page, int size);

    ApiResponse<?> filterProducts(FilterReqDTO filterReqDTO, int page, int size);

    ApiResponse<?> getTopSoldProducts(int page, int size);

    ApiResponse<?> getNewArrivals(int page, int size);
}

