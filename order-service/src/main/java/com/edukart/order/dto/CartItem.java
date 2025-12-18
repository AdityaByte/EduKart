package com.edukart.order.dto;

import com.edukart.order.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @JsonAlias("id")
    private String productID;
    @JsonAlias("name")
    private String productName;
    @JsonAlias("category")
    private ProductCategory productCategory;
    @JsonAlias("price")
    private BigDecimal productPrice;
}
