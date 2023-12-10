package dev.pr.habittracker.service;

import dev.pr.habittracker.model.Achievement;
import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.Tracker;
import dev.pr.habittracker.repository.AchievementRepository;
import dev.pr.habittracker.repository.PeopleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
    private AchievementService achievementService;
    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private PeopleRepository peopleRepository;

    @BeforeEach
    void setUp() {
        achievementService = new AchievementService(achievementRepository,peopleRepository);
    }

    @Test
    void canFindByPersonId() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.existsById(any())).willReturn(true);
        achievementService.findByPersonId(id);
        verify(achievementRepository).findByPersonId(any());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/days.csv")
    void canCreate(String days) {
        String[] parts = days.split("\\D+");
        Habit habit = Habit.builder().person(new Person()).build();
        Tracker tracker = Tracker.builder()
                .habitId("0d0000f0-c00b-00d0-0fa0-000b00aca0cc")
                .finished(true)
                .allDays(Integer.parseInt(parts[0]))
                .completedDays(Integer.parseInt(parts[1]))
                .habit(habit)
                .build();
        given(achievementRepository.existsByAchievement(any(),any())).willReturn(false);
        achievementService.create(tracker);

        ArgumentCaptor<Achievement> achievementArgumentCaptor = ArgumentCaptor.forClass(Achievement.class);
        verify(achievementRepository).save(achievementArgumentCaptor.capture());

        Achievement capturedAchievement = achievementArgumentCaptor.getValue();
    }
}