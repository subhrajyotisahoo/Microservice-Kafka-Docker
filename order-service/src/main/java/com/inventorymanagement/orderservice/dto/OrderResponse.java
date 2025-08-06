package com.inventorymanagement.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long productId;
    private int quantity;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status;
}
