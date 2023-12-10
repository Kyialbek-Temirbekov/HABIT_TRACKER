package dev.pr.habittracker.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementDto {
    private String id;
    private String title;
    private String description;
}
