package com.edukart.order.repository;

import com.edukart.order.enums.OrderStatus;
import com.edukart.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByUserIDAndOrderStatus(String userID, OrderStatus status);
}
