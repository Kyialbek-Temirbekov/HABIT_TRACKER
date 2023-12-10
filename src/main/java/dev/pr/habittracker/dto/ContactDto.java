package dev.pr.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String email;
    @NotNull
    @NotBlank
    @Size(max = 255)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String subject;
    @NotNull
    @NotBlank
    @Size(max = 1000)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String message;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createDt;
}
