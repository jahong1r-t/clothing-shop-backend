package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.req.ReviewReqDTO;

import java.util.UUID;

@RequestMapping("/api/review")
public interface ReviewController {
    @PostMapping("/products/{productId}")
    ResponseEntity<?> addReview(@AuthenticationPrincipal User user,
                                @PathVariable Long productId,
                                @RequestBody ReviewReqDTO dto);

    @GetMapping("/products/{productId}")
    ResponseEntity<?> getReviewsByProduct(@PathVariable Long productId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "6") int size);

    @GetMapping("/{reviewId}")
    ResponseEntity<?> getReviewById(@PathVariable UUID reviewId);

    @PutMapping("/{reviewId}")
    ResponseEntity<?> updateReview(@AuthenticationPrincipal User user,
                                   @PathVariable UUID reviewId,
                                   @RequestBody ReviewReqDTO dto);

    @DeleteMapping("/{reviewId}")
    ResponseEntity<?> deleteReview(@AuthenticationPrincipal User user,
                                   @PathVariable UUID reviewId);

    @GetMapping("/products/{productId}/rating")
    ResponseEntity<?> getAverageRating(@PathVariable Long productId);
}
