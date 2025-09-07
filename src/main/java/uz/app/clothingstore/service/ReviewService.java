package uz.app.clothingstore.service;

import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ReviewReqDTO;

import java.util.UUID;

public interface ReviewService {
    ApiResponse<?> addReview(User user, Long productId, ReviewReqDTO dto);

    ApiResponse<?> getReviewsByProduct(Long productId, int page, int size);

    ApiResponse<?> getReviewById(UUID reviewId);

    ApiResponse<?> updateReview(User user, UUID reviewId, ReviewReqDTO dto);

    ApiResponse<?> deleteReview(User user, UUID reviewId);
}
