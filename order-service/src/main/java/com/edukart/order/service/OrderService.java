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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    /***
     * Things we need to do to this function let us think one more time.
     * for placing an order we need to know about the cart.
     * 1. fetching the cart items using the cart-service.
     * 2. Rechecking the cart items to the product-service.
     * 3. Now before creating the order we need to check that the order is preexisted with the
     * same cart items or not if it does then we do return the same order despite creating a new order.
     *
     * Brainstorming over some things:
     * Like before fetching the product we can check through the order itself.
     *
     * Logic resides something like:
     * In the cart there were cartitems so we can check by ids.
     * @param userID
     * @return
     */

    public OrderResponse placeOrder(String userID) {

        // Making a synchronous request to the cart-service for fetching out the cart-details.
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-UID", userID);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartResponse> cartResponse = restTemplate.exchange(
                "http://cart-service/api/cart",
                HttpMethod.GET,
                entity,
                CartResponse.class);

        if (!cartResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to place the order because no cart was found, " + cartResponse.getBody());
        }

        CartResponse fetchedCart = cartResponse.getBody();

        // Since here we have to validate the order line times are not pre-existed to the previous order.
        Optional<Order> preexistedOrder = orderRepository.findByUserIDAndOrderStatus(userID, OrderStatus.PENDING);

        if (preexistedOrder.isPresent()) {
            List<CartItem> cartItems = fetchedCart.getCartItemList();
            List<OrderLineItem> orderLineItems = preexistedOrder.get().getOrderLineItems();

            if (cartItems.size() != orderLineItems.size()) {
                // Items are different.
                List<ProductResponse> productResponseList = fetchProductDetailsFromProductService(fetchedCart);
                // Now have to create an order.
                Order order = createOrder(userID, productResponseList);
                // Then in this we are saving the order.
                order = orderRepository.save(order);
                // Fetching the payment response.
                PaymentRequest paymentRequest = PaymentRequest
                        .builder()
                        .orderID(order.getId())
                        .userID(userID)
                        .amount(order.getTotalAmount())
                        .currency("INR")
                        .returnURL("http://order-service/api/order/" + order.getId() + "/success")
                        .build();

                String paymentURL = generatePaymentURL(paymentRequest);

                return OrderResponse
                        .builder()
                        .orderID(order.getId())
                        .status(OrderStatus.PENDING.name())
                        .paymentURL(paymentURL)
                        .totalAmount(order.getTotalAmount())
                        .build();
            } else {
                // Items are kind of same size.
                Set<String> cartProductIds = cartItems.stream()
                        .map(CartItem::getProductID)
                        .collect(Collectors.toSet());

                Set<String> orderProductIds = orderLineItems.stream()
                        .map(OrderLineItem::getProductID)
                        .collect(Collectors.toSet());

                if (!cartProductIds.equals(orderProductIds)) {
                    // If not equal in that case we need to make the product.
                    // Items are different.
                    List<ProductResponse> productResponseList = fetchProductDetailsFromProductService(fetchedCart);
                    // Now have to create an order.
                    Order order = createOrder(userID, productResponseList);
                    // Then in this we are saving the order.
                    order = orderRepository.save(order);
                    // Fetching the payment response.
                    PaymentRequest paymentRequest = PaymentRequest
                            .builder()
                            .orderID(order.getId())
                            .userID(userID)
                            .amount(order.getTotalAmount())
                            .currency("INR")
                            .returnURL("http://order-service/api/order/" + order.getId() + "/success")
                            .build();

                    String paymentURL = generatePaymentURL(paymentRequest);

                    return OrderResponse
                            .builder()
                            .orderID(order.getId())
                            .status(OrderStatus.PENDING.name())
                            .paymentURL(paymentURL)
                            .totalAmount(order.getTotalAmount())
                            .build();
                } else {
                    // Else we need to make the order and have to return it.
                    Order order = preexistedOrder.get();

                    PaymentRequest paymentRequest = PaymentRequest
                            .builder()
                            .orderID(order.getId())
                            .userID(userID)
                            .amount(order.getTotalAmount())
                            .currency("INR")
                            .returnURL("http://order-service/api/order/" + order.getId() + "/success")
                            .build();

                    return OrderResponse.builder()
                            .orderID(order.getId())
                            .status(String.valueOf(order.getOrderStatus()))
                            .paymentURL(generatePaymentURL(paymentRequest))
                            .totalAmount(order.getTotalAmount())
                            .build();
                }
            }
        }

        // Else if the order is not present we do have to do have to fetch the items and place an order.

        List<ProductResponse> productResponseList = fetchProductDetailsFromProductService(fetchedCart);
        // Now have to create an order.
        Order order = createOrder(userID, productResponseList);
        // Then in this we are saving the order.
        order = orderRepository.save(order);
        // Fetching the payment response.
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderID(order.getId())
                .userID(userID)
                .amount(order.getTotalAmount())
                .currency("INR")
                .returnURL("http://order-service/api/order/" + order.getId() + "/success")
                .build();

        String paymentURL = generatePaymentURL(paymentRequest);

        return OrderResponse
                .builder()
                .orderID(order.getId())
                .status(OrderStatus.PENDING.name())
                .paymentURL(paymentURL)
                .totalAmount(order.getTotalAmount())
                .build();
    }

    private String generatePaymentURL(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-UID", paymentRequest.getUserID());
        headers.setContentType(MediaType.APPLICATION_JSON);

        PaymentResponse paymentResponse = restTemplate
                .postForObject(
                        "http://payment-service/api/payment",
                        new HttpEntity<>(paymentRequest, headers),
                        PaymentResponse.class
                );

        assert paymentResponse != null;
        if (paymentResponse.getStatus().equals("FAILURE")) {
            throw new RuntimeException("Failed to generate the payment URI: " + paymentResponse.getMessage());
        }

        return paymentResponse.getPaymentURL();
    }

    private Order createOrder(String userID, List<ProductResponse> productResponseList) {
        Order order = Order
                .builder()
                .userID(userID)
                .totalAmount(productResponseList.stream()
                        .map(ProductResponse::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();

        List<OrderLineItem> orderLineItems = mapToOrderLineItem(productResponseList, order);
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    private List<ProductResponse> fetchProductDetailsFromProductService(CartResponse cartResponse) {
        // Communicating with the product service for validating the cart-items are valid or not.
        List<String> productIds = cartResponse.getCartItemList()
                .stream()
                .map(CartItem::getProductID)
                .toList();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString("http://product-service/api/product/ids")
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

        if (productResponse.getBody() == null || productResponse.getBody().isEmpty()) {
            throw new RuntimeException("No product exists, since the response size is 0");
        }

        return productResponse.getBody();
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

    private boolean isSameOrder(List<OrderLineItem> orderLineItems, List<CartItem> cartItems) {
        if (orderLineItems.size() != cartItems.size()) return false;
        for (CartItem cartItem: cartItems) {
            boolean match = orderLineItems.stream().anyMatch(oi ->
                    oi.getProductID().equals(cartItem.getProductID()));
            if (!match) return false;
        }

        return true;
    }

}