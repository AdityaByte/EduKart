package com.edukart.product.model;

import com.edukart.product.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_table")
public class Product {
    @Id
    private String productId;
    private String name;
    @Column(length = 500)
    private String description;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private BigDecimal price;
    private String filename;
}
