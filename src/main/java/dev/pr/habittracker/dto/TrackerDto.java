package dev.pr.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackerDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int allDays;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int completedDays;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean finished;
}
