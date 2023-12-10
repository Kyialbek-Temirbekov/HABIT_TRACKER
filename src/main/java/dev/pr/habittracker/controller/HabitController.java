package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.HabitDto;
import dev.pr.habittracker.dto.MessageDto;
import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.service.HabitService;
import dev.pr.habittracker.util.TermValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.pr.habittracker.util.FieldManager.getRequiredFields;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
@Validated
public class HabitController {
    private final HabitService habitService;
    private final ModelMapper modelMapper;
    @PostMapping("/{personId}")
    public ResponseEntity<?> create(@RequestBody @Valid HabitDto habitDto,
                                    @PathVariable("personId") String personId) {
        Habit habit = habitService.save(convertToHabit(habitDto), personId);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToHabitDto(habit));
    }
    @GetMapping("/reviewAll")
    public List<HabitDto> reviewAll() {
        return habitService.findAll().stream().map(this::convertToHabitDto).toList();
    }
    @GetMapping("/reviewAll/{personId}")
    public List<HabitDto> reviewAll(@PathVariable("personId") String id) {
        return habitService.findByPersonId(id).stream().map(this::convertToHabitDto).toList();
    }
    @GetMapping("/reviewActive/{personId}")
    public List<HabitDto> reviewActive(@PathVariable("personId") String id) {
        return habitService.findActiveHabitsByPersonId(id).stream().map(this::convertToHabitDto).toList();
    }
    @GetMapping("/reviewAllExtracted")
    public List<?> reviewAllExtracted(@RequestParam(required = false) String fields) {
        if(fields != null)
            return habitService.findAll().stream().map(habit -> getRequiredFields(habit,fields, Habit.class)).toList();
        else
            return habitService.findAll().stream().map(this::convertToHabitDto).toList();
    }
    @GetMapping("/review/{id}")
    public ResponseEntity<HabitDto> review(@PathVariable("id") String id) {
        return ResponseEntity.ok(convertToHabitDto(habitService.findOne(id)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody @Valid HabitDto habitDto) {
        Habit habit = habitService.update(id, convertToHabit(habitDto));
        return ResponseEntity.ok(convertToHabitDto(habit));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        habitService.delete(id);
        return ResponseEntity.ok(new MessageDto("Habit deleted successfully"));
    }
    @PatchMapping("/checkOff/{id}")
    public ResponseEntity<?> checkOff(@PathVariable("id") String id) {
        habitService.checkOffHabit(id);
        return ResponseEntity.ok(new MessageDto("Habit marked successfully"));
    }

    private HabitDto convertToHabitDto(Habit habit) {
        return modelMapper.map(habit, HabitDto.class);
    }

    private Habit convertToHabit(HabitDto habitDto) {
        return modelMapper.map(habitDto, Habit.class);
    }
}
