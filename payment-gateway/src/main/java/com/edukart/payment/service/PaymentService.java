package com.edukart.payment.service;

import com.edukart.payment.dto.Cart;
import com.edukart.payment.dto.Item;
import com.edukart.payment.dto.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Value("${stripe.secretKey}")
    private String SECRET_KEY;

    public PaymentResponse makePayment(Cart cart) {

        Stripe.apiKey = SECRET_KEY;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        cart.getItems()
                .stream()
                .filter(item -> !item.getId().isEmpty())
                .forEach(item -> {
                    lineItems.add(createLineItem(item));
                });

        if (lineItems.isEmpty()) {
            return PaymentResponse
                    .builder()
                    .status("FAILURE")
                    .message("No item exists")
                    .build();
        }

        SessionCreateParams params = SessionCreateParams
                .builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://product-service/payment/success")
                .setCancelUrl("http://product-service/payment/cancel")
                .addAllLineItem(lineItems)
                .build();

        Session session = null;

        try {
            session = Session.create(params);
        } catch (StripeException ex) {
            return  PaymentResponse
                    .builder()
                    .status("FAILURE")
                    .message(ex.getLocalizedMessage())
                    .build();
        }

        if (session == null) {
            return PaymentResponse
                    .builder()
                    .status("FAILURE")
                    .message("Session creation failed without exception")
                    .build();
        }

        return PaymentResponse
                .builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    private SessionCreateParams.LineItem createLineItem(Item item) {
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                .builder()
                .setName(item.getName())
                .build();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                .builder()
                .setUnitAmount(item.getAmount() * 100) // Price would be in rupee so multiplying it by 100 would make it rupee cause it accept the minimum unit.
                .setCurrency("INR")
                .setProductData(productData)
                .build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(item.getQuantity() != null ? item.getQuantity() : 1L)
                .setPriceData(priceData)
                .build();

        return lineItem;
    }
}
