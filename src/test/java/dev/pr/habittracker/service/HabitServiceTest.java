package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import dev.pr.habittracker.repository.HabitRepository;
import dev.pr.habittracker.repository.PeopleRepository;
import dev.pr.habittracker.repository.ReminderRepository;
import dev.pr.habittracker.repository.TrackerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {
    private HabitService habitService;
    private ReminderService reminderService;
    private TrackerService trackerService;
    @Mock
    private HabitRepository habitRepository;
    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private ReminderRepository reminderRepository;
    @Mock
    private TrackerRepository trackerRepository;
    @Mock
    private AchievementService achievementService;

    @BeforeEach
    void setUp() {
        reminderService = new ReminderService(reminderRepository,trackerRepository);
        trackerService = new TrackerService();
        habitService = new HabitService(habitRepository, peopleRepository,reminderService,trackerService,achievementService);
    }

    @Test
    void canSave() {
        Habit habit = generateHabit();
        given(peopleRepository.findById(any())).willReturn(Optional.of(new Person()));
        habitService.save(habit,"0d0000f0-c00b-00d0-0fa0-000b00aca0cc");

        ArgumentCaptor<Habit> habitArgumentCaptor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository).save(habitArgumentCaptor.capture());

        Habit capturedHabit = habitArgumentCaptor.getValue();
        Assertions.assertThat(capturedHabit).isEqualTo(habit);
    }

    @Test
    void canFindAll() {
        habitService.findAll();
        verify(habitRepository).findAll();
    }

    @Test
    void canFindByPersonId() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.existsById(any())).willReturn(true);
        habitService.findByPersonId(id);
        verify(habitRepository).findByPersonId(id);
    }

    @Test
    void canFindActiveHabitsByPersonId() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.existsById(any())).willReturn(true);
        habitService.findActiveHabitsByPersonId(id);
        verify(habitRepository).findActiveHabitsByPersonId(id);
    }

    @Test
    void givenIsActive() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isActiveMethod = habitService.getClass().getDeclaredMethod("isActive", Habit.class);
        isActiveMethod.setAccessible(true);
        Habit habit = generateHabit();

        boolean isActive = (boolean) isActiveMethod.invoke(habitService,habit);
        Assertions.assertThat(isActive).isTrue();

        habit.setGoal(Collections.singletonList(TimeOfDay.getTimeOfDay(true)));
        isActive = (boolean) isActiveMethod.invoke(habitService,habit);
        Assertions.assertThat(isActive).isTrue();

        habit.setGoal(Collections.singletonList(TimeOfDay.getTimeOfDay(false)));
        isActive = (boolean) isActiveMethod.invoke(habitService,habit);
        Assertions.assertThat(isActive).isFalse();
    }

    @Test
    void canFindOne() {
        String habitId = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(habitRepository.findById(any())).willReturn(Optional.of(generateHabit()));
        habitService.findOne(habitId);
        verify(habitRepository).findById(any());
    }

    @Test
    void willThrowWhenPersonNotFound() {
        String habitId = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(habitRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> habitService.findOne(habitId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("%s not found with the given input data %s: %s", "Habit","habitId",habitId));
    }

    @Test
    void canUpdate() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        Habit habit = generateHabit();
        given(habitRepository.existsById(any())).willReturn(true);
        habitService.update(id, habit);

        ArgumentCaptor<Habit> habitArgumentCaptor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository).save(habitArgumentCaptor.capture());

        Habit capturedHabit = habitArgumentCaptor.getValue();
        Assertions.assertThat(capturedHabit).isEqualTo(habit);
    }

    @Test
    void canDelete() {
        given(habitRepository.existsById(any())).willReturn(true);
        habitService.delete("0d0000f0-c00b-00d0-0fa0-000b00aca0cc");
        verify(habitRepository).deleteById(any());
    }

    @Test
    void checkOffHabit() {
        Habit habit = generateHabit();
        habit.setReminder(reminderService.produce(habit));
        habit.setTracker(trackerService.produce(habit));
        given(habitRepository.findById(any())).willReturn(Optional.of(habit));
        habitService.checkOffHabit("0d0000f0-c00b-00d0-0fa0-000b00aca0cc");

        ArgumentCaptor<Habit> habitArgumentCaptor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository).save(habitArgumentCaptor.capture());

        Habit capturedHabit = habitArgumentCaptor.getValue();
        Assertions.assertThat(capturedHabit).isEqualTo(habit);
    }

    private Habit generateHabit() {
        return Habit.builder()
                .title("Book")
                .description("will read Harry Potter")
                .goal(List.of(TimeOfDay.ALL_DAY))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(1))
                .frequency(Frequency.DAILY)
                .day(32)
                .build();
    }
}