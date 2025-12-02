package com.edukart.product.repository;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

public interface ProductRepository extends MongoRepository<Product, String> {
}