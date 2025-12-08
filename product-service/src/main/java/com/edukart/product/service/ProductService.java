package com.edukart.product.service;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.model.File;
import com.edukart.product.model.Product;
import com.edukart.product.repository.FileRepository;
import com.edukart.product.repository.ProductRepository;
import com.edukart.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;
    private final ReviewRepository reviewRepository;

    public Product addProduct(MultipartFile file, ProductRequest productRequest) {
        try {
            Product product = mapToProduct(productRequest);

            // Calling cloudinary service for uploading the file.
            File fileModel = cloudinaryService.uploadFile(file);
            fileModel.setId(new ObjectId().toHexString());
            fileModel.setProduct(product);

            product.setFile(fileModel);

            // Saving the file
            productRepository.save(product);
            fileRepository.save(fileModel);

            return product;
        } catch (IOException exception) {
            throw new RuntimeException("Unable to upload the file," + exception.getMessage());
        }
    }

    private Product mapToProduct(ProductRequest productRequest) {
        return Product
                .builder()
                .id(new ObjectId().toHexString())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .build();
    }

    public void removeProduct(String productId) {
        productRepository.deleteById(productId);
        // Since after deleting the product we also need to delete the reviews and the files associated to it too.
        fileRepository
                .findByProductId(productId)
                .ifPresent(file -> {
                    // Here we need to remove the file from the cloudinary too.
                    try {
                        cloudinaryService.removeFile(file.getPublicId(), file.getResourceType());
                    } catch (IOException exception) {
                        throw new RuntimeException("Failed to remove the file from cloudinary, " + exception.getMessage());
                    } finally {
                        // Along with that we need to remove the file from the db too.
                        fileRepository.delete(file);
                    }
                });
        // Along with that we need to remove the reviews too.
        reviewRepository.deleteByProductId(productId);
    }

    public List<ProductResponse> fetchAllProduct() {
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            return mapToListOfProductResponse(products);
        }
        return Collections.emptyList();
    }

    public ProductResponse fetchProduct(String productId) {
        return (ProductResponse) productRepository.findById(productId)
                .map(product -> mapToListOfProductResponse(List.of(product)))
                .orElse(null);
    }

    private List<ProductResponse> mapToListOfProductResponse(List<Product> products) {
        return products
                .stream()
                .map(product -> ProductResponse
                        .builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .category(product.getCategory()).build())
                .toList();
    }

    public List<ProductResponse> fetchProductByIds(List<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        return mapToListOfProductResponse(products);
    }
}
