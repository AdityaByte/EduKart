package com.edukart.order.service;

import com.edukart.order.dto.OrderLineItemDto;
import com.edukart.order.dto.OrderRequest;
import com.edukart.order.model.Order;
import com.edukart.order.model.OrderLineItem;
import com.edukart.order.repository.OrderRepository;
import com.edukart.order.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final Utility utility;

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItem> orderLineItemList = mapToOrderLineItem(orderRequest.getOrderLineItemDtoList());
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderNumber(utility.generateOrderNumber())
                .orderLineItemList(orderLineItemList)
                .build();
        orderRepository.save(order);
    }

    private List<OrderLineItem> mapToOrderLineItem(List<OrderLineItemDto> orderLineItemDtoList) {
        return orderLineItemDtoList.stream()
                .map(orderLineItemDto -> OrderLineItem.builder()
                        .id(orderLineItemDto.getOrderId())
                        .skuCode(orderLineItemDto.getSkuCode())
                        .price(orderLineItemDto.getPrice())
                        .build())
                .toList();
    }

}
