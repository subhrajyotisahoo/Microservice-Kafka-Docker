package com.inventorymanagement.notificationservice.service;

import com.inventorymanagement.notificationservice.client.ProductClient;
import com.inventorymanagement.notificationservice.client.UserClient;
import com.inventorymanagement.notificationservice.dto.OrderEvent;
import com.inventorymanagement.notificationservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final UserClient userClient;
    private final ProductClient productClient;

    public void sendNotification(OrderEvent event) {
        log.info("Fetching user info for ID: {}", event.getUserId());

        UserDto user = userClient.getUserById(event.getUserId());
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.warn("User email not found for ID: {}", user.getId());
            return;
        }

        // Prepare email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Order Confirmation - Order ID: " + event.getOrderId());
        message.setText(buildEmailBody(event, user));

        // Send email
        mailSender.send(message);

        log.info("Email sent to: {}", user.getEmail());
    }

    private String buildEmailBody(OrderEvent event, UserDto user) {
        return String.format("Hello %s,\n\nYour order #%d has been confirmed.\n" +
                        "Product ID: %d\nQuantity: %d\nTotal Price: %.2f\n\nThank you!",
                user.getName(), event.getOrderId(), event.getProductId(),
                event.getQuantity(), event.getTotalPrice());
    }
}
