package com.edukart.order.service;

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

    public void placeOrder(OrderRequest orderRequest) {
        try {

            // Need to check the OrderListItems is in the stock or not.
            // Request synchronous request product service.
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromUriString("http://localhost:8080/api/product/ids");

            orderRequest.getOrderLineItemDtoList()
                    .stream()
                    .filter(item -> !item.getSkuCode().isEmpty())
                    .forEach(item -> uriBuilder.queryParam("productId", item.getSkuCode()));

            URI uri = uriBuilder.build().toUri();

            ResponseEntity<List<ProductResponse>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    // To fetch a list of object we use the ParametrizedTypeReference
                    new ParameterizedTypeReference<List<ProductResponse>>() {
                    }
            );

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ProductNotAvailableException(Objects.requireNonNull(response.getBody()).toString());
            }

            List<OrderLineItem> orderLineItemList = mapToOrderLineItem(Objects.requireNonNull(response.getBody()));
            Order order = Order.builder()
                    .orderId(UUID.randomUUID().toString())
                    .orderNumber(utility.generateOrderNumber())
                    .orderLineItemList(orderLineItemList)
                    .orderStatus(OrderStatus.PENDING)
                    .build();

            // There could be the payment gateway step.
            log.info("Payment has been done successfully");

            // Here we trigger an event to the notification service for sending that the order is been placed.
            log.info("Notification has been sent to the user email");

            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);

        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProductNotAvailableException(ex.getResponseBodyAsString());
            }
            throw new RuntimeException(ex.getMessage());
        }
    }

    private List<OrderLineItem> mapToOrderLineItem(List<ProductResponse> productResponses) {
        return productResponses
                .stream()
                .map(productResponse -> OrderLineItem
                        .builder()
                        .orderId(utility.generateOrderId())
                        .skuCode(productResponse.getProductId())
                        .price(productResponse.getPrice())
                        .build())
                .toList();
    }

}
