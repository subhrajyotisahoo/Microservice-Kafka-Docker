package com.inventorymanagement.orderservice.repository;


import com.inventorymanagement.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
