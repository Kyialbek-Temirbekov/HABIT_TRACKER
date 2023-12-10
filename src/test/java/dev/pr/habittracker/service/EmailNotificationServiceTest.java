package dev.pr.habittracker.service;

import dev.pr.habittracker.dto.NotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceTest {
    private EmailNotificationService emailNotificationService;
    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        emailNotificationService = new EmailNotificationService(javaMailSender);
    }

    @Test
    void sendNotification() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to");
        message.setSubject("subject");
        message.setText("text");
        emailNotificationService.sendNotification(new NotificationRequest("to","subject","text"));
        verify(javaMailSender).send(message);
    }
}