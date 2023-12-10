package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.AchievementDto;
import dev.pr.habittracker.model.Achievement;
import dev.pr.habittracker.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
@Validated
public class AchievementController {
    private final AchievementService achievementService;
    private final ModelMapper modelMapper;
    @GetMapping("/{personId}")
    public List<AchievementDto> findByPersonId(@PathVariable("personId") String id) {
        return achievementService.findByPersonId(id).stream().map(this::convertToAchievementDto).toList();
    }

    private AchievementDto convertToAchievementDto(Achievement achievement) {
        return modelMapper.map(achievement, AchievementDto.class);
    }
}
