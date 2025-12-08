package com.edukart.cart.service;

import com.edukart.cart.dto.CartItem;
import com.edukart.cart.dto.CartResponse;
import com.edukart.cart.model.Cart;
import com.edukart.cart.pojo.Product;
import com.edukart.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    public void addToCart(String userID, String productID) {

        productID = productID.trim();

        // Here we need to first call the product service and fetch the product details as per the product id.
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                "http://product-service/api/product/ids?id={id}",
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

        Cart cart = cartRepository.findByUserID(userID)
                        .orElseGet(() -> Cart
                                .builder()
                                .userID(userID)
                                .cartItemList(new ArrayList<>())
                                .build());

        cart.addProduct(product);
        cartRepository.save(cart);
    }

    public void removeFromCart(String userID, String productID) {
        // Here we have to remove the item from the cart.
        cartRepository
                .findByUserID(userID)
                .ifPresentOrElse(cart -> {
                    cart.removeProduct(productID);
                    cartRepository.save(cart);
                }, () -> {
                    throw new RuntimeException("No cart found for user: " + userID);
                });
    }

    public CartResponse fetchCart(String userID) {
        return cartRepository.findByUserID(userID)
                .map(this::mapToCartResponse)
                .orElseThrow(() -> new RuntimeException("No cart found for userID: " + userID));  // Throw if absent
    }

    private CartResponse mapToCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .cartItemList(cart.getCartItemList().stream()
                        .map(cartItem -> CartItem.builder()
                                .productID(cartItem.getProductID())
                                .productName(cartItem.getProductName())
                                .productCategory(cartItem.getProductCategory())
                                .productPrice(cartItem.getProductPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}