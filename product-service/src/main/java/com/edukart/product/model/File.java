package com.edukart.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "files")
public class File {
    @Id
    private String id;

    @Field(name = "secure_url")
    private String secureUrl;

    @Field(name = "public_id")
    private String publicId;

    @Field(name = "resource_type")
    private String resourceType; // zip, image or anything.

    @DBRef
    private Product product;
}
