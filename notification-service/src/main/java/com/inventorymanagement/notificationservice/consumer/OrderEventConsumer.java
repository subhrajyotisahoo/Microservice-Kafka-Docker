package com.inventorymanagement.notificationservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorymanagement.notificationservice.dto.OrderEvent;
import com.inventorymanagement.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final NotificationService notificationService;



    @KafkaListener(topics = "order-events", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrderEvent(OrderEvent event) {
        log.info("Received order event: {}", event);
        notificationService.sendNotification(event);
    }
}
