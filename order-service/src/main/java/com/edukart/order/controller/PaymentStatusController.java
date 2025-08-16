package com.edukart.order.controller;

import com.edukart.order.dto.OrderResponse;
import com.edukart.order.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentStatusController {

    private final PaymentStatusService paymentService;

    @GetMapping("/success")
    public ResponseEntity<OrderResponse> successPageHandler(@RequestParam("session_id") String sessionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentService.handleSuccessPayment(sessionId));
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPageHandler(@RequestParam("session_id") String sessionId){
        paymentService.handleCancelPayment(sessionId);
        return ResponseEntity.ok("Payment failed");
    }
}
