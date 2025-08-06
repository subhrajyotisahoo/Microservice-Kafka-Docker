package com.inventorymanagement.orderservice.exception;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}
