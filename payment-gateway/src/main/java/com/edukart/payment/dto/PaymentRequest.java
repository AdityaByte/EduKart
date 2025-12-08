package com.edukart.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String orderID;
    private BigDecimal amount;
    private String currency;
    private String returnURL; // success/failure redirect URL.
    private String description;
}
