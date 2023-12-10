package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.MessageDto;
import dev.pr.habittracker.dto.SharedHabitDto;
import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.model.SharedHabit;
import dev.pr.habittracker.service.SharedHabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sharedHabit")
@RequiredArgsConstructor
@Validated
public class SharedHabitController {
    private final SharedHabitService sharedHabitService;
    private final ModelMapper modelMapper;
    @PostMapping()
    public ResponseEntity<SharedHabitDto> create(@RequestBody @Valid SharedHabitDto sharedHabitDto) {
        SharedHabit sharedHabit = sharedHabitService.save(convertToSharedHabit(sharedHabitDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToSharedHabitDto(sharedHabit));
    }
    @GetMapping("/reviewAll")
    public List<SharedHabitDto> reviewAll() {
        return sharedHabitService.findAll().stream().map(this::convertToSharedHabitDto).toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        sharedHabitService.delete(id);
        return ResponseEntity.ok(new MessageDto("Habit deleted successfully"));
    }
    private SharedHabitDto convertToSharedHabitDto(SharedHabit habit) {
        return modelMapper.map(habit, SharedHabitDto.class);
    }

    private SharedHabit convertToSharedHabit(SharedHabitDto habitDto) {
        return modelMapper.map(habitDto, SharedHabit.class);
    }
}
