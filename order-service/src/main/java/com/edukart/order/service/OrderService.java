package com.edukart.order.service;

import com.edukart.order.dto.OrderLineItemDto;
import com.edukart.order.dto.OrderRequest;
import com.edukart.order.dto.ProductResponse;
import com.edukart.order.dto.ProductResponseList;
import com.edukart.order.enums.OrderStatus;
import com.edukart.order.exceptions.ProductNotAvailableException;
import com.edukart.order.model.Order;
import com.edukart.order.model.OrderLineItem;
import com.edukart.order.repository.OrderRepository;
import com.edukart.order.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final Utility utility;

    private final RestTemplate restTemplate;

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {

        // Need to check the OrderListItems is in the stock or not.
        // Request synchronous request product service.
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/product/");

        orderRequest.getOrderLineItemDtoList()
                .stream()
                .filter(item -> !item.getSkuCode().isEmpty())
                .forEach(item -> uriBuilder.queryParam("productId", item.getSkuCode()));

        URI uri = uriBuilder.build().toUri();

        ResponseEntity<ProductResponseList> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                ProductResponseList.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ProductNotAvailableException("Product is not available in the stock");
        }


        List<OrderLineItem> orderLineItemList = mapToOrderLineItem(response.getBody());
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderNumber(utility.generateOrderNumber())
                .orderLineItemList(orderLineItemList)
                .orderStatus(OrderStatus.PENDING)
                .build();

        // There could be the payment gateway step.

        // Here we trigger an event to the notification service for sending that the order is been placed.


        orderRepository.save(order);
    }

    private List<OrderLineItem> mapToOrderLineItem(ProductResponseList productResponseList) {
        return productResponseList.getProductResponses()
                .stream()
                .map(productResponse -> OrderLineItem
                        .builder()
                        .skuCode(productResponse.getProductId())
                        .price(productResponse.getPrice())
                        .build())
                .toList();
    }

}
