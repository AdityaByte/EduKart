package com.edukart.order.controller;

import com.edukart.order.dto.OrderRequest;
import com.edukart.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(
            @RequestBody List<OrderRequest> orderRequests,
            @RequestHeader("User-Email") String email
            ) {
        System.out.println("User-Email is: " + email);
        String response = orderService.placeOrder(orderRequests, email);
        if (response.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong at the server! Try again later.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "Working";
    }

}
