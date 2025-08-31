package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/statistic")
public interface StatisticController {

    @GetMapping("/products/{productId}")
    ResponseEntity<?> getProductStatistic(@PathVariable Long productId);

    @GetMapping("/products")
    ResponseEntity<?> getAllProductStatistics(@RequestParam int page, @RequestParam int size);

    @GetMapping("/variants/{variantId}")
    ResponseEntity<?> getVariantStatistic(@PathVariable Long variantId);
}
