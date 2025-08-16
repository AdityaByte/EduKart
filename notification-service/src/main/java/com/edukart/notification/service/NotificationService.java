package com.edukart.notification.service;

import com.edukart.notification.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

//    private final JavaMailSender javaMailSender;

    public void sendNotification(Message message) {
        try {
            log.info("Sending notification to {}", message.getDestination());
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(message.getDestination());
//            mailMessage.setSubject(message.getSubject());
//            mailMessage.setText(message.getMessage());
//            javaMailSender.send(mailMessage);
            log.info(message.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
