package com.edukart.product.controller;

import com.edukart.product.dto.ProductRequest;
import com.edukart.product.dto.ProductResponse;
import com.edukart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String addProducts(@RequestBody List<ProductRequest> productRequests) {
        service.addProducts(productRequests);
        return "Products inserted successfully";
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
