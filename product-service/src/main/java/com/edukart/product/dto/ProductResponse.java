package com.edukart.product.dto;

import com.edukart.product.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
}
