package com.edukart.product.service;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.enums.ProductCategory;
import com.edukart.product.model.Product;
import com.edukart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void addProducts(List<ProductRequest> productRequestList) {
        List<Product> productList = productRequestList.stream()
                .map((product) ->
                    Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name(product.getName())
                            .description(product.getDescription())
                            .category(ProductCategory.valueOf(product.getCategory()))
                            .build())
                .toList();
        repository.saveAll(productList);
    }

    public List<ProductResponse> getProducts() {
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .build())
                .toList();
    }

    public void updateProduct(ProductRequest productRequest) {
        repository.findById(productRequest.getId())
                .map(existingProduct -> {
                    existingProduct.setName(productRequest.getName());
                    existingProduct.setDescription(productRequest.getDescription());
                    existingProduct.setCategory(ProductCategory.valueOf(productRequest.getCategory()));
                    return repository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productRequest.getId() ));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

}
