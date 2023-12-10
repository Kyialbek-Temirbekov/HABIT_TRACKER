package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.Reminder;
import dev.pr.habittracker.model.Tracker;
import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import dev.pr.habittracker.service.ReminderService;
import dev.pr.habittracker.service.TrackerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootTest
class ReminderRepositoryTest {
    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private ReminderService reminderService;
    @Autowired
    private TrackerService trackerService;
    private Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .name("Asan")
                .email("asan@gmail.com")
                .password("asan12345")
                .enabled(true)
                .build();
        this.person = peopleRepository.save(person);
    }

    @Test
    void expiredNonFinished() {
        Habit habit = createHabit();
        Reminder reminder = reminderService.produce(habit);
        reminder.setNextTargetDate(LocalDate.now().minusDays(1));
        habit.setReminder(reminder);
        Tracker tracker = trackerService.produce(habit);
        habit.setTracker(tracker);
        habitRepository.save(habit);

        Assertions.assertThat(reminderRepository.findExpired()).isNotEmpty();
    }
    @Test
    void expiredFinished() {
        Habit habit = createHabit();
        Reminder reminder = reminderService.produce(habit);
        reminder.setNextTargetDate(LocalDate.now().minusDays(1));
        habit.setReminder(reminder);
        Tracker tracker = trackerService.produce(habit);
        tracker.setFinished(true);
        habit.setTracker(tracker);
        habitRepository.save(habit);

        Assertions.assertThat(reminderRepository.findExpired()).isEmpty();
    }
    @Test
    void nonExpiredNonFinished() {
        Habit habit = createHabit();
        Reminder reminder = reminderService.produce(habit);
        reminder.setNextTargetDate(LocalDate.now().plusDays(1));
        habit.setReminder(reminder);
        Tracker tracker = trackerService.produce(habit);
        habit.setTracker(tracker);
        habitRepository.save(habit);

        Assertions.assertThat(reminderRepository.findExpired()).isEmpty();
    }
    @Test
    void nonExpiredFinished() {
        Habit habit = createHabit();
        Reminder reminder = reminderService.produce(habit);
        reminder.setNextTargetDate(LocalDate.now().plusDays(1));
        habit.setReminder(reminder);
        Tracker tracker = trackerService.produce(habit);
        tracker.setFinished(true);
        habit.setTracker(tracker);
        habitRepository.save(habit);

        Assertions.assertThat(reminderRepository.findExpired()).isEmpty();
    }
    private Habit createHabit() {
        return Habit.builder()
                .title("Run")
                .description("Every Monday")
                .goal(Collections.singletonList(TimeOfDay.ALL_DAY))
                .frequency(Frequency.WEEKLY)
                .day(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .person(this.person)
                .build();
    }
}