package com.edukart.product.model;

import com.edukart.product.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;
    private String description;
    private ProductCategory category;

    private BigDecimal price;

    @DBRef
    private File file;

    // One-to-Many relationship.
    @DBRef
    private List<Review> reviews = new ArrayList<>();
}
