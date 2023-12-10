package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.MessageDto;
import dev.pr.habittracker.dto.PermissionRequest;
import dev.pr.habittracker.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@Validated
public class PermissionController {
    private final PermissionService permissionService;
    @PatchMapping("/authorizeWithRole")
    public ResponseEntity<?> authorizeWithRole(@RequestBody PermissionRequest permissionRequest) {
        permissionService.authorizeWithRole(permissionRequest.getRole(), permissionRequest.getId());
        return ResponseEntity.ok(new MessageDto("User with id " + permissionRequest.getId() + " authorized with role " + permissionRequest.getRole().toString()));
    }
    @PatchMapping("/disallowWithoutRole")
    public ResponseEntity<?> disallowWithoutRole(@RequestBody PermissionRequest permissionRequest) {
        permissionService.disallowWithoutRole(permissionRequest.getRole(), permissionRequest.getId());
        return ResponseEntity.ok(new MessageDto("User with id " + permissionRequest.getId() + " disallowed without role " + permissionRequest.getRole().toString()));
    }
    @PatchMapping("/lock/{id}")
    public ResponseEntity<?> lock(@PathVariable("id") String id) {
        permissionService.lock(id);
        return ResponseEntity.ok(new MessageDto(String.format("User with id %d locked successfully", id)));
    }
    @PatchMapping("/unlock/{id}")
    public ResponseEntity<?> unlock(@PathVariable("id") String id) {
        permissionService.unlock(id);
        return ResponseEntity.ok(new MessageDto(String.format("User with id %d unlocked successfully", id)));
    }
}
