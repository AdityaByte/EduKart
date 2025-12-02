package com.edukart.product.repository;

import com.edukart.product.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File, String> {
    long deleteByProductId(String productId);

    Optional<File> findByProductId(String productId);
}
