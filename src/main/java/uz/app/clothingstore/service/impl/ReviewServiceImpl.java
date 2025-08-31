package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.Review;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ReviewReqDTO;
import uz.app.clothingstore.payload.resp.ReviewRespDTO;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductStatisticRepository;
import uz.app.clothingstore.repostory.ReviewRepository;
import uz.app.clothingstore.service.ReviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final ProductStatisticRepository productStatisticRepository;

    @Override
    public ApiResponse<?> addReview(User user, Long productId, ReviewReqDTO dto) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .user(user)
                .product(product)
                .build();

        reviewRepository.save(review);

        return ApiResponse.success(
                "Review added successfully",
                Map.of("id", review.getId()));
    }

    @Override
    public ApiResponse<?> getReviewsByProduct(Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Review> all = reviewRepository.findAllActiveByProductId(productId, pageable);

        List<ReviewRespDTO> list = all.stream().map(r ->
                ReviewRespDTO.builder()
                        .id(r.getId())
                        .productId(r.getProduct().getId())
                        .userId(r.getUser().getId())
                        .firstName(r.getUser().getFirstName())
                        .lastName(r.getUser().getLastName())
                        .rating(r.getRating())
                        .createdAt(r.getCreatedAt())
                        .comment(r.getComment())
                        .build()).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("products", list);
        response.put("currentPage", all.getNumber());
        response.put("totalItems", all.getTotalElements());
        response.put("totalPages", all.getTotalPages());

        return ApiResponse.success("", response);
    }

    @Override
    public ApiResponse<?> getReviewById(UUID reviewId) {
        Review review = reviewRepository.findActiveById(reviewId)
                .orElseThrow(() -> new ItemNotFoundException("Review not found"));

        ReviewRespDTO build = ReviewRespDTO.builder()
                .id(review.getId())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .firstName(review.getUser().getFirstName())
                .lastName(review.getUser().getLastName())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .comment(review.getComment())
                .build();

        return ApiResponse.success("Review", build);
    }

    @Override
    public ApiResponse<?> updateReview(User user, UUID reviewId, ReviewReqDTO dto) {
        Review review = reviewRepository.findActiveById(reviewId)
                .orElseThrow(() -> new ItemNotFoundException("Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        reviewRepository.save(review);

        return ApiResponse.success("Review updated successfully", Map.of("id", review.getId()));
    }

    @Override
    public ApiResponse<?> deleteReview(User user, UUID reviewId) {
        Review review = reviewRepository.findActiveById(reviewId)
                .orElseThrow(() -> new ItemNotFoundException("Review not found"));

        review.setDeleted(true);
        review.setActive(false);
        reviewRepository.save(review);

        return ApiResponse.success("Review deleted successfully", null);
    }

    @Override
    public ApiResponse<?> getAverageRating(Long productId) {

        return null;
    }
}
