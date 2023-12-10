package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collections;


@DataJpaTest
class HabitRepositoryTest {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private HabitRepository habitRepository;
    private Person person;

    @BeforeEach
    public void setUp() {
        person = Person.builder()
                .name("Asan")
                .email("asan@gmail.com")
                .password("asan12345")
                .enabled(true)
                .build();
        this.person = peopleRepository.save(person);
    }

    @Test
    void doesNotExistsByPersonId() {
        Assertions.assertThat(habitRepository.findByPersonId(person.getId())).isEmpty();
    }
    @Test
    void existsByPersonId() {
        saveHabit();
        Assertions.assertThat(habitRepository.findByPersonId(person.getId())).isNotEmpty();
    }
    @Test
    void doesNotExistsActiveHabitsByPersonId() {
        saveHabit();
        Assertions.assertThat(habitRepository.findActiveHabitsByPersonId(person.getId())).isEmpty();
    }
    @Test
    void existsActiveHabitsByPersonId() {
        saveActiveHabit();
        Assertions.assertThat(habitRepository.findByPersonId(person.getId())).isNotEmpty();
    }

    private void saveActiveHabit() {
        Habit habit = Habit.builder()
                .title("Run")
                .description("Every Monday")
                .goal(Collections.singletonList(TimeOfDay.ALL_DAY))
                .frequency(Frequency.WEEKLY)
                .day(LocalDate.now().getDayOfWeek().getValue())
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now().plusMonths(1))
                .person(this.person)
                .build();
        habitRepository.save(habit);
    }

    private void saveHabit() {
        Habit habit = Habit.builder()
                .title("Run")
                .description("Every Monday")
                .goal(Collections.singletonList(TimeOfDay.ALL_DAY))
                .frequency(Frequency.WEEKLY)
                .day(LocalDate.now().plusDays(6).getDayOfWeek().getValue())
                .startDate(LocalDate.now().minusMonths(1))
                .endDate(LocalDate.now().plusMonths(1))
                .person(this.person)
                .build();
        habitRepository.save(habit);
    }
}