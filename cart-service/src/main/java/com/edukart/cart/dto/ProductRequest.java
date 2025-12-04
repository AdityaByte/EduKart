package com.edukart.cart.dto;

import com.edukart.cart.enums.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class ProductRequest {
    @NonNull
    private String id;
    private String productName;
    private BigDecimal price;
    private ProductCategory category;
    // This one is crucial and can't be null.
    @NonNull
    private String userID;
}
