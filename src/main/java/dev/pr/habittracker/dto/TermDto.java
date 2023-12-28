package dev.pr.habittracker.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TermDto {
    @NotNull()
    @FutureOrPresent()
    private LocalDate startDate;
    @NotNull()
    @Future()
    private LocalDate endDate;
}
