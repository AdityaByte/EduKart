package com.edukart.cart.service;

import com.edukart.cart.dto.ProductRequest;
import com.edukart.cart.model.Cart;
import com.edukart.cart.model.CartItem;
import com.edukart.cart.pojo.Product;
import com.edukart.cart.repository.CartItemRepository;
import com.edukart.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;

    public void addToCart(String userID, String productID) {

        productID = productID.trim();

        // Here we need to first call the product service and fetch the product details as per the product id.
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                "http://localhost:8081/api/product/ids?id={id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {},
                productID
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Product not found: " + response.getStatusCode());
        }

        List<Product> productList = response.getBody();
        if (productList.isEmpty() || productList == null) {
            throw new RuntimeException("No product found.");
        }

        // Else we need to typecast the list to single product.
        Product product = productList.get(0);

        // Here we need to add a product to the cart.
        cartRepository.findByUserID(userID)
                .ifPresentOrElse(cart -> {
                    cart
                            .addProduct(product);
                }, () -> {
                    Cart cart = Cart
                            .builder()
                            .userID(userID)
                            .build();
                    CartItem cartItem = CartItem
                            .builder()
                            .cart(cart)
                            .productID(product.getId())
                            .productName(product.getName())
                            .productCategory(product.getCategory())
                            .productPrice(product.getPrice())
                            .build();

                    cart.setCartItemList(List.of(cartItem));
                    // Now insert those fields.
                    Cart savedCart = cartRepository.save(cart);
                    cartItem.setCart(savedCart);
                    cartItemRepository.save(cartItem);
                });
    }

    public void removeFromCart(String userID, String productID) {
        // Here we have to remove the item from the cart.
        cartRepository
                .findByUserID(userID)
                .ifPresentOrElse(cart -> {
                    // If the user cart is present we have to remove the item from the cart.
                    cart.removeProduct(productID);
                    cartItemRepository.deleteByUserIdAndProductId(userID, productID);
                    // When the thing is done we need to save the cart.
                    cartRepository.save(cart);
                }, () -> {
                    throw new RuntimeException("No cart found of the user having id: "+ userID);
                });
    }
}