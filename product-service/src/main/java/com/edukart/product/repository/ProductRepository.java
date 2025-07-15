package com.edukart.product.repository;

import com.edukart.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductIdIn(List<String> productIds);
}