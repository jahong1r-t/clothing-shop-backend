package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.ReviewController;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ReviewReqDTO;
import uz.app.clothingstore.service.ReviewService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {
    private final ReviewService reviewService;

    @Override
    public ResponseEntity<?> addReview(User user, Long productId, ReviewReqDTO dto) {
        ApiResponse<?> apiResponse = reviewService.addReview(user, productId, dto);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getReviewsByProduct(Long productId, int page, int size) {
        ApiResponse<?> apiResponse = reviewService.getReviewsByProduct(productId, page, size);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getReviewById(UUID reviewId) {
        ApiResponse<?> apiResponse = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> updateReview(User user, UUID reviewId, ReviewReqDTO dto) {
        ApiResponse<?> apiResponse = reviewService.updateReview(user, reviewId, dto);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> deleteReview(User user, UUID reviewId) {
        ApiResponse<?> apiResponse = reviewService.deleteReview(user, reviewId);
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    public ResponseEntity<?> getAverageRating(Long productId) {
        return null;
    }
}
