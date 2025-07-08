package com.edukart.order.controller;

import com.edukart.order.dto.OrderRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    public void placeOrder(@RequestBody OrderRequest orderRequest) {

    }

}
