package com.edukart.order.service;

import com.edukart.order.dto.*;
import com.edukart.order.enums.OrderStatus;
import com.edukart.order.model.Order;
import com.edukart.order.model.OrderLineItem;
import com.edukart.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    public OrderResponse placeOrder(String userID) {
        // Here we need to make a synchronous request to the cart-service for fetching out the cart-details.
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-UID", userID);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartResponse> cartResponse = restTemplate.exchange(
                "http://localhost:8083/api/cart",
                HttpMethod.GET,
                entity,
                CartResponse.class);

        if (!cartResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to place the order because no cart was found, " + cartResponse.getBody());
        }

        // Else we need to validate the items are exists or not.
        // And gets the product details.

        // Communicating with the product service for validating the cart-items are valid or not.
        List<String> productIds = cartResponse.getBody().getCartItemList()
                .stream()
                .map(CartItem::getProductID)
                .toList();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://localhost:8081/api/product/ids")
                .queryParam("id", productIds);

        ResponseEntity<List<ProductResponse>> productResponse = restTemplate
                .exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProductResponse>>() {}
                );

        if (!productResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to get the valid product response, " + productResponse.getBody());
        }

        List<ProductResponse> productResponseList = productResponse.getBody();

        // Now we have to make the order and do all the necessary things.
        Order order = Order
                .builder()
                .userID(userID)
                .totalAmount(productResponseList.stream()
                        .map(ProductResponse::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        List<OrderLineItem> orderLineItems = mapToOrderLineItem(productResponseList, order);
        order.setOrderLineItems(orderLineItems);

        order = orderRepository.save(order);

        // Now we have to call to the payment service for payment.
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderID(order.getId())
                .userID(userID)
                .amount(order.getTotalAmount())
                .currency("INR")
                .returnURL("http://localhost:8080/api/order/" + order.getId() + "/success")
                .build();

        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("X-Auth-UID", userID);
        headers1.setContentType(MediaType.APPLICATION_JSON);

        PaymentResponse paymentResponse = restTemplate
                .postForObject(
                        "http://localhost:8082/api/payment",
                        new HttpEntity<>(paymentRequest, headers1),
                        PaymentResponse.class
                );

        assert paymentResponse != null;
        if (paymentResponse.getStatus().equals("FAILURE")) {
            throw new RuntimeException("Failed to generate the payment URI: " + paymentResponse.getMessage());
        }

        return OrderResponse
                .builder()
                .orderID(order.getId())
                .status(OrderStatus.PENDING.name())
                .paymentURL(paymentResponse.getPaymentURL())
                .totalAmount(order.getTotalAmount())
                .build();
    }

    private List<OrderLineItem> mapToOrderLineItem(List<ProductResponse> productResponseList, Order order) {
        return productResponseList
                .stream()
                .map(productResponse -> {
                    return OrderLineItem.builder()
                            .order(order)
                            .productName(productResponse.getName())
                            .productID(productResponse.getProductId())
                            .productCategory(productResponse.getCategory())
                            .productPrice(productResponse.getPrice())
                            .build();
                })
                .toList();
    }

}