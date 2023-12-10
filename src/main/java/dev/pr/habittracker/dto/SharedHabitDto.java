package dev.pr.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SharedHabitDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @NotBlank()
    @Size(max = 50)
    private String title;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @NotBlank()
    @Size(max = 500)
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @NotEmpty()
    private List<TimeOfDay> goal;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    private Frequency frequency;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @Positive()
    private int day;
}
