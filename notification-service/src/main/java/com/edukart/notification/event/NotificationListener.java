package com.edukart.notification.event;

import com.edukart.notification.dto.Message;
import com.edukart.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationService service;

    @KafkaListener(topics = "notification.message")
    public void listenToNotification(Message message) {
        // Here we call the service layer
        service.sendNotification(message);
    }
}
