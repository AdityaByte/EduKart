package com.edukart.order.model;

import com.edukart.order.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_line_item")
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // many-to-one relationship to the OrderLineItems.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "product_id")
    private String productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer quantity = 1;
}