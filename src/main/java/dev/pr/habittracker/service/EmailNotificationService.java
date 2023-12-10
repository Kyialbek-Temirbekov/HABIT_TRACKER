package dev.pr.habittracker.service;

import dev.pr.habittracker.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {
    private final JavaMailSender mailSender;
    @Override
    public void sendNotification(NotificationRequest notificationRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationRequest.getRecipient());
        message.setSubject(notificationRequest.getSubject());
        message.setText(notificationRequest.getText());

        mailSender.send(message);
    }
}
