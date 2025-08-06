package com.Inventorymanagement.userservice.kafka.producer;

import com.Inventorymanagement.userservice.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC="user-created";
    public void sendUserCreatedEvent(User user){
        String message= String.format("User created: ID=%d, Email=%s", user.getId(),user.getEmail());
        log.info("Sending Kafka message: {}",message);
        kafkaTemplate.send(TOPIC, message);
    }
}
