package com.edukart.product.model;

import com.edukart.product.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;
    private String name;
    @Column(length = 500)
    private String description;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}
