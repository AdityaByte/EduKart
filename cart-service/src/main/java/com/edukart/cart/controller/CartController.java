package com.edukart.cart.controller;

import com.edukart.cart.dto.AddCartRequest;
import com.edukart.cart.dto.CartResponse;
import com.edukart.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String handleAddToCart(@RequestHeader("X-Auth-UID") String userID,  @RequestParam("id") String productID) {
        cartService.addToCart(userID, productID);
        return "Item added to the cart successfully";
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String handleRemoveFromCart(@RequestHeader("X-Auth-UID") String userID, @RequestParam("id") String productID) {
        cartService.removeFromCart(userID, productID);
        return "Item removed from cart successfully";
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CartResponse handleGetCart(@RequestHeader("X-Auth-UID") String userID) {
        // Here we need to the find the cart as per the userid.
        return cartService.fetchCart(userID);
    }

}
