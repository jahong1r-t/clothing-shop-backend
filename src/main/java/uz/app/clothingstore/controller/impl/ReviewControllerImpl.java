package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.ReviewController;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.req.ReviewReqDTO;

@RestController
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {
    @Override
    public ResponseEntity<?> addReview(User user, Long productId, ReviewReqDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReviewsByProduct(Long productId, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<?> getReviewById(Long reviewId) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateReview(User user, String reviewId, ReviewReqDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteReview(User user, String reviewId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAverageRating(Long productId) {
        return null;
    }
}
