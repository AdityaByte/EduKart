package com.edukart.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String id;
    private String name;
    private Long amount;
    private Long quantity;
}
