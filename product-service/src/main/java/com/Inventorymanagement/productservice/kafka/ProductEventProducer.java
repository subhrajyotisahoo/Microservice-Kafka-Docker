package com.Inventorymanagement.productservice.kafka;




import com.Inventorymanagement.productservice.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendProductCreatedEvent(Product product) {
        String message = "Product created: " + product.getName();
        kafkaTemplate.send("product-topic", message);
        log.info("Published product event to Kafka: {}", message);
    }
}
