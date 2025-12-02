package com.edukart.product.service;

import com.edukart.product.dto.ReviewRequest;
import com.edukart.product.dto.ReviewResponse;
import com.edukart.product.model.Product;
import com.edukart.product.model.Review;
import com.edukart.product.repository.ProductRepository;
import com.edukart.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public void addReview(ReviewRequest reviewRequest) {
        // Finding the product.
        productRepository.findById(reviewRequest.getProductId())
                .ifPresentOrElse(product -> {
                    // When the product is present we need to add a review.
                    Review review = mapToReview(reviewRequest);
                    review.setProduct(product);
                    product.getReviews().add(review);

                    // Saving both the documents.
                    productRepository.save(product);
                    reviewRepository.save(review);
                },
                () -> {
                    throw new RuntimeException("No product find of the product ID");
                });
    }

    private Review mapToReview(ReviewRequest reviewRequest) {
        return Review
                .builder()
                .id(new ObjectId().toHexString())
                .user(reviewRequest.getUser())
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // In the future, we do replace it with the Event-driven cascade deletion for better scalability.
    public void deleteReview(String reviewId) {
        // Removing the review by the reviewId;
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(review -> {
                    // Removing the review from the review db.
                    reviewRepository.delete(review);
                    // Along with that we have to remove from the product.
                    productRepository
                            .findById(review.getProduct().getId())
                            .ifPresent(product -> {
                                product.getReviews().remove(review);
                                productRepository.save(product);
                            });
                }, () -> {
                    throw new RuntimeException("No Review find of the ID");
                });
    }

    public List<ReviewResponse> fetchReviewsByProductId(String productId) {
        return reviewRepository
                .findByProductId(productId)
                .stream().map(review -> mapToReviewResponse(review))
                .toList();
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        return ReviewResponse
                .builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .user(review.getUser())
                .productId(review.getProduct().getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
