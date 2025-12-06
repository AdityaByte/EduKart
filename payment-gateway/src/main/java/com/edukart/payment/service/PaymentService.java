package com.edukart.payment.service;

import com.edukart.payment.dto.PaymentRequest;
import com.edukart.payment.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    @Value("${stripe.secret-key}")
    private String SECRET_KEY;

    @Value("${frontend.origin:http://localhost:3000}")
    private String FRONTEND_ORIGIN;

    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        Stripe.apiKey = SECRET_KEY;

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(paymentRequest.getCurrency())
                                .setUnitAmount(paymentRequest
                                        .getAmount()
                                        .multiply(BigDecimal.valueOf(100))
                                        .setScale(0, RoundingMode.UNNECESSARY)
                                        .longValueExact())
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Order #" + paymentRequest.getOrderID())
                                                .build()
                                )
                                .build()
                )
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .putAllMetadata(Map.of(
                        "orderID", paymentRequest.getOrderID(),
                        "userID", paymentRequest.getUserID()
                ))
                .setSuccessUrl(FRONTEND_ORIGIN + "/order/success?orderID=" + paymentRequest.getOrderID() + "&session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(FRONTEND_ORIGIN + "/order/fail?orderID=" + paymentRequest.getOrderID())
                .build();

        try {
            Session session = Session.create(params);
            return PaymentResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionID(session.getId())
                    .paymentURL(session.getUrl())
                    .build();
        } catch (StripeException ex) {
            log.error("Stripe payment failed for order {}: {}", paymentRequest.getOrderID(), ex.getMessage());
            return PaymentResponse.builder()
                    .status("FAILURE")
                    .message(ex.getLocalizedMessage())
                    .build();
        }
    }
}