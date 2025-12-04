package com.edukart.cart.model;

import com.edukart.cart.enums.ProductCategory;
import com.edukart.cart.pojo.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Many items can belong to a single cart, so many-to-one relationship.
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private Cart cart;

    // The cart-item belongs to which product.
    // So we have to include a field which meant for the product.
    @Column(name = "product_id")
    private String productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_category")
    private ProductCategory productCategory;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    public CartItem(Cart cart, Product product) {
        this.cart = cart;
        this.productID = product.getId();
        this.productName = product.getName();
        this.productCategory = product.getCategory();
        this.productPrice = product.getPrice();
    }
}
