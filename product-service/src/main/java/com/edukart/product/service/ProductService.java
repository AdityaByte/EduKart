package com.edukart.product.service;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.enums.ProductCategory;
import com.edukart.product.exceptions.FailedToDeleteFileException;
import com.edukart.product.exceptions.ProductNotAvailableException;
import com.edukart.product.model.Product;
import com.edukart.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final RestTemplate restTemplate;
    @Value("${supabase.api.url}")
    private String API_URL;
    @Value("${supabase.api.servicekey}")
    private String API_SERVICE_KEY;
    @Value("${supabase.api.bucketname}")
    private String BUCKET_NAME;

    public ResponseEntity<String> addProduct(ProductRequest productRequest, MultipartFile file) {
        try {
            Product product = Product
                    .builder()
                    .productId(UUID.randomUUID().toString())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .category(ProductCategory.valueOf(productRequest.getCategory()))
                    .filename(file.getOriginalFilename())
                    .build();

            // Since we have to make a POST request to the Supabase Database for storing the file.
            ResponseEntity<String> responseFromSupabase = saveFileToSupabase(file);
            if (responseFromSupabase.getStatusCode().is2xxSuccessful()) {
                // If the file is successfully saved to the db
                // Then we have to save the metadata of the file too
                repository.save(product);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Product saved successfully");
            }

            return responseFromSupabase;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to add product! Try again later.");
        }
    }

    private ResponseEntity<String> saveFileToSupabase(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_SERVICE_KEY);
        headers.set("Authorization", "Bearer " + API_SERVICE_KEY);
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + file.getOriginalFilename(),
                HttpMethod.POST,
                entity,
                String.class
        );

        return response;
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

    public List<ProductResponse> getProductByIds(List<String> skuCodes) {
        List<Product> foundProducts = repository.findByProductIdIn(skuCodes);

        if (foundProducts.size() != skuCodes.size()) {
            // Using set so that no duplicate id should be present.
            Set<String> foundIds = foundProducts
                    .stream()
                    .map(Product::getProductId)
                    .collect(Collectors.toSet());

            List<String> missingIds = skuCodes
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
        // Firstly we need to fetch the product via the productId
        Optional<String> filename = repository.findById(id)
                .map(product -> Optional.of(product.getFilename()))
                .orElse(Optional.empty());

        filename.ifPresent(fn -> {
            // If the filename exists like we fetched out the file in that case we need to send the delete request
            // to the supabase database
            ResponseEntity<Void> deleteRequestRespose = deleteFileFromStorage(fn);
            if (deleteRequestRespose.getStatusCode().is2xxSuccessful()) {
                repository.deleteById(id);
            } else {
                throw new FailedToDeleteFileException("Failed to delete the file from the database");
            }
        });
    }

    private ResponseEntity<Void> deleteFileFromStorage(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", API_SERVICE_KEY);
        headers.set("Authorization", "Bearer " + API_SERVICE_KEY);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = String.format("%s/storage/v1/object/%s/%s", API_URL, BUCKET_NAME, filename);

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
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
