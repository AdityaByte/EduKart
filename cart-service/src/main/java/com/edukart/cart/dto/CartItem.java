package com.edukart.cart.dto;

import com.edukart.cart.enums.ProductCategory;
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
    private String productID;
    private String productName;
    private ProductCategory productCategory;
    private BigDecimal productPrice;
}
