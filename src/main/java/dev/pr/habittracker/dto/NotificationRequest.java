package dev.pr.habittracker.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NotificationRequest {
    private String recipient;
    private String subject;
    private String text;
}
