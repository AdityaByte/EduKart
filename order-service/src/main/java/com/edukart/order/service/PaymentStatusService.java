package com.edukart.order.service;

import com.edukart.order.dto.OrderResponse;
import com.edukart.order.dto.ProductResponse;
import com.edukart.order.enums.OrderStatus;
import com.edukart.order.model.Order;
import com.edukart.order.repository.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentStatusService {

    private final OrderRepository orderRepository;

    public OrderResponse handleSuccessPayment(String sessionId) {
        OrderResponse orderResponse = new OrderResponse();
        try {
            Session session = Session.retrieve(sessionId);
            String orderId = session.getMetadata().get("orderId");
            // Now we need to update the status of the order.
            if(orderId != null) {
                Optional<Order> fetchedOrder = orderRepository.findById(orderId);
                fetchedOrder.ifPresent(order -> {
                    order.setOrderStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                });
                return OrderResponse
                        .builder()
                        .orderId(fetchedOrder.get().getOrderId())
                        .items(fetchedOrder.get().getOrderLineItemList().stream()
                                .map(lineItem -> ProductResponse.builder()
                                        .name(lineItem.getName())
                                        .productId(lineItem.getSkuCode())
                                        .price(lineItem.getPrice())
                                        .build()).toList())
                        .build();
            }
            return orderResponse;
        } catch (StripeException ex) {
            log.error(ex.getMessage());
            return orderResponse;
        }
    }

    public void handleCancelPayment(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            String orderId = session.getMetadata().get("orderId");
            if (orderId != null) {
                Optional<Order> fetchedOrder = orderRepository.findById(orderId);
                fetchedOrder.ifPresent(order -> {
                    order.setOrderStatus(OrderStatus.FAILED);
                    orderRepository.save(order);
                });
            }
        } catch (StripeException ex) {
            log.error(ex.getMessage());
        }
    }
}
