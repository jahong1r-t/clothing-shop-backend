package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.SearchController;
import uz.app.clothingstore.payload.req.FilterReqDTO;

@RestController
@RequiredArgsConstructor
public class SearchControllerImpl implements SearchController {
    @Override
    public ResponseEntity<?> searchProductsByName(String query, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> filterProducts(FilterReqDTO filterReqDTO, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> getTopSoldProducts(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> getPopularProducts(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> getNewArrivals(int page, int size) {
        return null;
    }
}
