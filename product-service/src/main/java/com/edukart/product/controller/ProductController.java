package com.edukart.product.controller;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct(
            @RequestPart("product")
            ProductRequest product,
            @RequestPart("file")
            MultipartFile file
    ) {
        return service.addProduct(product, file);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/ids")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(@RequestParam("productId") List<String> productIds) {
        return service.getProductByIds(productIds);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String updateProduct(@RequestBody ProductRequest productRequest) {
        service.updateProduct(productRequest);
        return "Product updated successfully";
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteProduct(@RequestParam("productId") String id) {
        service.deleteProduct(id);
        return "Product has been deleted..";
    }

}
