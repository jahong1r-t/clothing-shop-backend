package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.StatisticController;

@RestController
@RequiredArgsConstructor
public class StatisticControllerImpl implements StatisticController {
    @Override
    public ResponseEntity<?> getProductStatistic(Long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllProductStatistics() {
        return null;
    }

    @Override
    public ResponseEntity<?> getVariantStatistic(Long variantId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductVariantStatistics(Long productId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVariantStatistics() {
        return null;
    }
}
