package com.edukart.cart.controller;

import com.edukart.cart.dto.AddCartRequest;
import com.edukart.cart.dto.CartResponse;
import com.edukart.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String handleAddToCart(@RequestBody AddCartRequest cartRequest) {
        cartService.addToCart(cartRequest.getUserID(), cartRequest.getProductID());
        return "Item added to the cart successfully";
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String handleRemoveFromCart(@RequestBody Map<String, String> deleteCartRequest) {
        cartService.removeFromCart(deleteCartRequest.get("userID"), deleteCartRequest.get("productID"));
        return "Item removed from cart successfully";
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CartResponse handleGetCart(@PathVariable("id") String id) {
        // Here we need to the find the cart as per the userid.
        return cartService.fetchCart(id);
    }

}
