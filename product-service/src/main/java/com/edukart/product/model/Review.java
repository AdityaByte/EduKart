package com.edukart.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private int rating;
    private String comment;

    @CreatedDate
    @Field(name = "created_at")
    private LocalDateTime createdAt;

    // Many reviews can belong to a particular product.
    // Many to One mapping for nosql db.
    @DBRef
    private Product product;

    // This review belongs to which user.
    // Since we are handling the user end at the firebase so don't have the access of that.
    // So we are just saving the username.
    private String user;
}
