package com.edukart.product.repository;

import com.edukart.product.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);

    long deleteByProductId(String productId);
}
