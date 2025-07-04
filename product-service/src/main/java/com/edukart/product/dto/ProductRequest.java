package com.edukart.product.dto;

import com.edukart.product.enums.ProductCategory;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private String category;
}
