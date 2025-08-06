package com.inventorymanagement.orderservice.service;
//
//import com.inventorymanagement.orderservice.exception.OrderNotFoundException;
//import com.inventorymanagement.orderservice.client.ProductClient;
//import com.inventorymanagement.orderservice.dto.OrderRequest;
//import com.inventorymanagement.orderservice.dto.OrderResponse;
//import com.inventorymanagement.orderservice.dto.ProductResponse;
//import com.inventorymanagement.orderservice.entity.Order;
//import com.inventorymanagement.orderservice.kafka.OrderProducer;
//import com.inventorymanagement.orderservice.mapper.OrderMapper;
//import com.inventorymanagement.orderservice.repository.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//    private final OrderProducer orderProducer;
//    private final ProductClient productClient;
//
//    public OrderResponse createOrder(OrderRequest request) {
//        // Fetch product details via Feign client
//        ProductResponse product = productClient.getProductById(request.getProductId());
//
//        // Calculate total price using product price
//        double totalPrice = product.getPrice().doubleValue() * request.getQuantity();
//
//        Order order = Order.builder()
//                .userId(request.getUserId())
//                .productId(request.getProductId())
//                .quantity(request.getQuantity())
//                .totalPrice(totalPrice)
//                .orderDate(LocalDateTime.now())
//                .build();
//
//        Order savedOrder = orderRepository.save(order);
//        log.info("Order placed: {}", savedOrder);
//
//        // Send event to Kafka
//        orderProducer.sendOrderEvent(savedOrder);
//
//        return OrderMapper.toResponse(savedOrder);
//    }
//
//    public List<OrderResponse> getAllOrders() {
//        return orderRepository.findAll().stream()
//                .map(OrderMapper::toResponse)
//                .collect(Collectors.toList());
//    }
//
//    public OrderResponse getOrderById(Long id) {
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
//        return OrderMapper.toResponse(order);
//    }
//}


import com.inventorymanagement.orderservice.client.ProductClient;
import com.inventorymanagement.orderservice.dto.OrderRequest;
import com.inventorymanagement.orderservice.dto.OrderResponse;
import com.inventorymanagement.orderservice.dto.ProductResponse;
import com.inventorymanagement.orderservice.entity.Order;
import com.inventorymanagement.orderservice.exception.OrderNotFoundException;
import com.inventorymanagement.orderservice.exception.ServiceUnavailableException;
import com.inventorymanagement.orderservice.kafka.OrderProducer;
import com.inventorymanagement.orderservice.mapper.OrderMapper;
import com.inventorymanagement.orderservice.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final ProductClient productClient;

    private static final String PRODUCT_SERVICE = "productService";

    /**
     * Creates an order and sends an event to Kafka.
     */
    public OrderResponse createOrder(OrderRequest request) {
        // Fetch product from product-service via Feign
        ProductResponse product = fetchProduct(request.getProductId());

        double totalPrice = product.getPrice().doubleValue() * request.getQuantity();

        Order order = Order.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order placed: {}", savedOrder);

        // Send Kafka event
        orderProducer.sendOrderEvent(savedOrder);

        return OrderMapper.toResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        return OrderMapper.toResponse(order);
    }

    /**
     * Calls Product Service to fetch product info.
     * If service is down, fallback method throws exception.
     */
    @CircuitBreaker(name = PRODUCT_SERVICE, fallbackMethod = "getProductFallback")
    public ProductResponse fetchProduct(Long productId) {
        log.info("Calling product-service for productId: {}", productId);
        return productClient.getProductById(productId);
    }

    /**
     * Fallback method if product-service is down.
     * No dummy product is returned; instead, an exception is thrown.
     */
    public ProductResponse getProductFallback(Long productId, Throwable throwable) {
        log.error("Product Service is down for productId {}. Error: {}", productId, throwable.toString());
        throw new ServiceUnavailableException("Product service is currently unavailable. Please try again later.");
    }
}
