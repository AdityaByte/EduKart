package com.edukart.product.controller;

import com.edukart.product.dto.ReviewRequest;
import com.edukart.product.dto.ReviewResponse;
import com.edukart.product.model.Review;
import com.edukart.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReviewController:
 * This mainly handles the reviews.
 */

@RestController
@RequestMapping("/api/product/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String addReview(@RequestHeader("X-Auth-UID") String userID, @RequestBody ReviewRequest reviewRequest) {
        reviewService.addReview(userID, reviewRequest);
        return "Review added successfully";
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String removeReview(@RequestParam("id") String id) {
        reviewService.deleteReview(id);
        return "Review removed successfully";
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getAllProductReviews(@RequestParam("productId") String id) {
        return reviewService.fetchReviewsByProductId(id);
    }

}