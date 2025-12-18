package com.edukart.order.dto;

import com.edukart.order.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    @JsonAlias("id")
    private String productId;
    private String name;
    private ProductCategory category;
    private BigDecimal price;
}