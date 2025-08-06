package com.inventorymanagement.orderservice.kafka;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorymanagement.orderservice.dto.OrderResponse;
import com.inventorymanagement.orderservice.entity.Order;
import com.inventorymanagement.orderservice.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String TOPIC = "order-events";

    public void sendOrderEvent(Order savedOrder) {
        try {

            OrderResponse response = OrderMapper.toResponse(savedOrder);
            String orderJson = objectMapper.writeValueAsString(response);

           // String orderJson = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, orderJson);
            log.info("Order event sent to Kafka: {}", orderJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to send order event", e);
        }
    }
}
