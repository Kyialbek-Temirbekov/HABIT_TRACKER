package dev.pr.habittracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @NotNull
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 255, message = "must contain more than 8 and less then 255 characters")
    private String password;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private boolean enabled;
    @JsonIgnore
    private boolean nonLocked;
}
