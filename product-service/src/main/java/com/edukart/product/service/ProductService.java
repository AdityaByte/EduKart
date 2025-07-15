package com.edukart.product.service;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.enums.ProductCategory;
import com.edukart.product.exceptions.ProductNotAvailableException;
import com.edukart.product.model.Product;
import com.edukart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public void addProducts(List<ProductRequest> productRequestList) {
        List<Product> productList = productRequestList.stream()
                .map((product) ->
                    Product.builder()
                            .productId(UUID.randomUUID().toString())
                            .name(product.getName())
                            .description(product.getDescription())
                            .category(ProductCategory.valueOf(product.getCategory()))
                            .price(product.getPrice())
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
                        .productId(product.getProductId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

    public List<ProductResponse> getProductByIds(List<String> productIds) {
        List<Product> foundProducts = repository.findByProductIdIn(productIds);

        if (foundProducts.size() != productIds.size()) {
            // Using set so that no duplicate id should be present.
            Set<String> foundIds = foundProducts
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toSet());

            List<String> missingIds = productIds
                    .stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ProductNotAvailableException("Products are not in stock of product ids: " + missingIds.toString());
        }

        return mapToProductResponse(foundProducts);
    }

    public void updateProduct(ProductRequest productRequest) {
        repository.findById(productRequest.getProductId())
                .map(existingProduct -> {
                    existingProduct.setName(productRequest.getName());
                    existingProduct.setDescription(productRequest.getDescription());
                    existingProduct.setCategory(ProductCategory.valueOf(productRequest.getCategory()));
                    return repository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productRequest.getProductId() ));
    }

    public void deleteProduct(String id) {
        repository.deleteById(id);
    }

    private List<ProductResponse> mapToProductResponse(List<Product> products) {
        return products.stream()
                .map(product -> ProductResponse
                        .builder()
                        .productId(product.getProductId())
                        .name(product.getName())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

}
