package dev.pr.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitDto {
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
    @FutureOrPresent()
    private LocalDate startDate;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @Future()
    private LocalDate endDate;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    private Frequency frequency;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull()
    @Positive()
    private int day;

}
