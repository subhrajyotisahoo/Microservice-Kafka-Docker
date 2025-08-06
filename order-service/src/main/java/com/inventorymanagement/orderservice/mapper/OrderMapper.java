package com.inventorymanagement.orderservice.mapper;

import com.inventorymanagement.orderservice.dto.OrderResponse;
import com.inventorymanagement.orderservice.entity.Order;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .build();
    }
}
