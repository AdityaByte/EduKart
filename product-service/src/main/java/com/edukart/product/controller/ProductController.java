package com.edukart.product.controller;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.model.Product;
import com.edukart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProduct(
            @RequestPart("product")
            ProductRequest product,
            @RequestPart("file")
            MultipartFile file
    ) {
        return productService.addProduct(file, product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ProductResponse> getProducts() {
        return productService.fetchAllProduct();
    }

    @GetMapping("/ids")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(@RequestParam("id") List<String> productIds) {
        return productService.fetchProductByIds(productIds);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteProduct(@RequestParam String id) {
        productService.removeProduct(id);
        return "Product has been deleted";
    }
}
