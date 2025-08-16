package com.edukart.payment.controller;

import com.edukart.payment.dto.Cart;
import com.edukart.payment.dto.PaymentResponse;
import com.edukart.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> paymentHandler(@RequestBody Cart cart) {
        PaymentResponse response = paymentService.makePayment(cart);
        if (response.getStatus().equals("FAILURE")) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response.getMessage());
        }
        return ResponseEntity
                .ok(response.getSessionUrl());
    }
}
