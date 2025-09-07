package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.SearchController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.FilterReqDTO;
import uz.app.clothingstore.service.SearchService;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {
    private final SearchService searchService;

    @Override
    public ResponseEntity<?> searchProductsByName(String query, int page, int size) {
        ApiResponse<?> apiResponse = searchService.searchProductsByName(query, page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> filterProducts(FilterReqDTO filterReqDTO, int page, int size) {
        filterReqDTO.dto();
        ApiResponse<?> apiResponse = searchService.filterProducts(filterReqDTO, page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getTopSoldProducts(int page, int size) {
        ApiResponse<?> apiResponse = searchService.getTopSoldProducts(page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getNewArrivals(int page, int size) {
        ApiResponse<?> apiResponse = searchService.getNewArrivals(page, size);
        return ResponseEntity.ok(apiResponse);
    }
}
