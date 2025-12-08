package com.edukart.cart.dto;

import com.edukart.cart.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private String productID;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("category")
    private ProductCategory productCategory;
    @JsonProperty("price")
    private BigDecimal productPrice;
}
