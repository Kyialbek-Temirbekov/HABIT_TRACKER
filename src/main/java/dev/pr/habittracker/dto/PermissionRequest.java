package dev.pr.habittracker.dto;

import dev.pr.habittracker.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionRequest {
    private String id;
    private Role role;
}
