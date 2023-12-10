package dev.pr.habittracker.service;

import dev.pr.habittracker.model.SharedHabit;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import dev.pr.habittracker.repository.SharedHabitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SharedHabitServiceTest {
    private SharedHabitService sharedHabitService;
    @Mock
    private SharedHabitRepository sharedHabitRepository;

    @BeforeEach
    void setUp() {
        sharedHabitService = new SharedHabitService(sharedHabitRepository);
    }

    @Test
    void canSave() {
        SharedHabit habit = SharedHabit.builder()
                .title("title")
                .description("description")
                .goal(Collections.singletonList(TimeOfDay.ALL_DAY))
                .frequency(Frequency.WEEKLY)
                .day(1)
                .build();
        sharedHabitService.save(habit);

        ArgumentCaptor<SharedHabit> sharedHabitArgumentCaptor = ArgumentCaptor.forClass(SharedHabit.class);
        verify(sharedHabitRepository).save(sharedHabitArgumentCaptor.capture());

        SharedHabit capturedHabit = sharedHabitArgumentCaptor.getValue();
        Assertions.assertThat(capturedHabit).isEqualTo(habit);
    }

    @Test
    void findAll() {
        sharedHabitService.findAll();
        verify(sharedHabitRepository).findAll();
    }

    @Test
    void delete() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(sharedHabitRepository.existsById(any())).willReturn(true);
        sharedHabitService.delete(id);

        ArgumentCaptor<String> sharedHabitArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(sharedHabitRepository).deleteById(sharedHabitArgumentCaptor.capture());

        String capturedId = sharedHabitArgumentCaptor.getValue();
        Assertions.assertThat(capturedId).isEqualTo(id);
    }
}