package com.edukart.payment.controller;

import com.edukart.payment.dto.PaymentRequest;
import com.edukart.payment.dto.PaymentResponse;
import com.edukart.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse handlePayment(@RequestHeader("X-Auth-UID") String userID, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.makePayment(userID, paymentRequest);
    }
}
