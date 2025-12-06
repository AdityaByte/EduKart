package com.edukart.order.controller;

import com.edukart.order.dto.OrderRequest;
import com.edukart.order.dto.OrderResponse;
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

    @GetMapping("/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OrderResponse placeOrder(@PathVariable("userID") String userID) {
        return orderService.placeOrder(userID);
    }
}
