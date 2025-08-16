package com.edukart.order.service;

import com.edukart.order.dto.CartRequest;
import com.edukart.order.dto.ItemRequest;
import com.edukart.order.dto.OrderRequest;
import com.edukart.order.dto.ProductResponse;
import com.edukart.order.enums.OrderStatus;
import com.edukart.order.exceptions.ProductNotAvailableException;
import com.edukart.order.model.Order;
import com.edukart.order.model.OrderLineItem;
import com.edukart.order.repository.OrderRepository;
import com.edukart.order.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final Utility utility;
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    public String placeOrder(List<OrderRequest> orderRequests, String email) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromUriString("http://product-service/api/product/ids");

        orderRequests.stream()
                .filter(orderRequest -> !orderRequest.getSkuCode().isEmpty())
                .forEach(item -> uriComponentsBuilder.queryParam("skuCode", item.getSkuCode()));

        URI uri = uriComponentsBuilder.build().toUri();

        ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<ProductResponse>>() {
                }
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ProductNotAvailableException(Objects.requireNonNull(response.getBody().toString()));
        }

        List<OrderLineItem> orderLineItemList = mapToOrderLineItem(Objects.requireNonNull(response.getBody()));
        Order order = Order.builder()
                .orderId(utility.generateOrderId())
                .orderLineItemList(orderLineItemList)
                .orderStatus(OrderStatus.PENDING)
                .build();

        // When we get the order we need to send the request to the payment-service.
        String sessionURL = makePaymentRequest(order);
        if (sessionURL.isEmpty()) {
            return null;
        }

        // Else we need to forward the URL to client.
        return sessionURL;
    }

    // Payment request must be synchronous.
    private String makePaymentRequest(Order order) {
        CartRequest cartRequest = mapToCartRequest(order);
        // Now I have to send the request to the payment service
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CartRequest> entity = new HttpEntity<>(cartRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://payment-gateway/api/payment",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Payment gateway response message: {}", response.getBody());
            return null;
        }
        return response.getBody();
    }

    private CartRequest mapToCartRequest(Order order){
        return CartRequest
                .builder()
                .orderId(order.getOrderId())
                .items(order.getOrderLineItemList()
                        .stream()
                        .map(orderLineItem -> ItemRequest
                                .builder()
                                .id(orderLineItem.getSkuCode())
                                .name(orderLineItem.getName())
                                .amount(orderLineItem.getPrice().longValueExact())
                                .quantity(1L)
                                .build()
                        )
                        .toList()
                ).build();
    }



    private List<OrderLineItem> mapToOrderLineItem(List<ProductResponse> productResponses) {
        return productResponses
                .stream()
                .map(productResponse -> OrderLineItem
                        .builder()
                        .orderId(utility.generateOrderId())
                        .skuCode(productResponse.getProductId())
                        .name(productResponse.getName())
                        .price(productResponse.getPrice())
                        .build())
                .toList();
    }

}
