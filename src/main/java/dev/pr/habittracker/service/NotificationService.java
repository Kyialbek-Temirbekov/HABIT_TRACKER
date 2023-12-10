package dev.pr.habittracker.service;

import dev.pr.habittracker.dto.NotificationRequest;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);
}
