package com.edukart.cart.model;

import com.edukart.cart.pojo.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // This field is useful of.
    // The cart belongs to which user.
    @Column(name = "user_id")
    private String userID;

    // A cart can have many cart-items.
    // So OneToMany relationship lies.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItemList = new ArrayList<>();

    public void addProduct(Product product) {
        cartItemList
                .forEach(cartItem -> {
                    if (!cartItem.getProductID().equals(product.getId())) {
                        cartItemList.add(new CartItem(this, product));
                    }
                });
    }

    public void removeProduct(String productID) {
        cartItemList
                .removeIf(item -> item.getProductID().equals(productID));
    }
}
