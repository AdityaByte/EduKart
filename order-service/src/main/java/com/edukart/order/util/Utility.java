package com.edukart.order.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class Utility {

    private final SecureRandom secureRandom;

    public String generateOrderNumber() {
        return "order" + secureRandom.nextInt(10000);
    }
}
