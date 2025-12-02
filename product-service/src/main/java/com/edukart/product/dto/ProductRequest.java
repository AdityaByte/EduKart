package com.edukart.product.dto;

import com.edukart.product.enums.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private String description;
    private ProductCategory category;
    private BigDecimal price;
}
