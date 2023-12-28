package dev.pr.habittracker.service;

import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Reminder;
import dev.pr.habittracker.model.Tracker;
import dev.pr.habittracker.repository.ReminderRepository;
import dev.pr.habittracker.repository.TrackerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final TrackerRepository trackerRepository;
    public Reminder produce(Habit habit) {
        return Reminder.builder()
                .nextTargetDate(getFirstTargetDate(habit))
                .marked(false)
                .habit(habit)
                .person(habit.getPerson())
                .build();
    }

    private LocalDate getFirstTargetDate(Habit habit) {
        LocalDate targetDate = habit.getTerm().getStartDate();
        switch (habit.getFrequency()) {
            case DAILY -> {
                return targetDate;
            }
            case WEEKLY -> {
                targetDate = habit.getTerm().getStartDate().with(DayOfWeek.of(habit.getDay())).isBefore(habit.getTerm().getStartDate()) ?
                        habit.getTerm().getStartDate().plusWeeks(1).with(DayOfWeek.of(habit.getDay())) :
                        habit.getTerm().getStartDate().with(DayOfWeek.of(habit.getDay()));
                if(targetDate.isAfter(habit.getTerm().getEndDate()))
                    return null;
                return targetDate;
            }
            case MONTHLY -> {
                targetDate = habit.getTerm().getStartDate().withDayOfMonth(habit.getDay()).isBefore(habit.getTerm().getStartDate()) ?
                        habit.getTerm().getStartDate().plusMonths(1).withDayOfMonth(habit.getDay()) :
                        habit.getTerm().getStartDate().withDayOfMonth(habit.getDay());
                if(targetDate.isAfter(habit.getTerm().getEndDate()))
                    return null;
                return targetDate;
            }
            default -> {
                return null;
            }
        }
    }
    private void setNextTargetDate(Reminder reminder) {
        Habit habit = reminder.getHabit();
        reminder.setNextTargetDate(getNextTargetDate(habit));
        reminder.setMarked(false);
    }
    protected LocalDate getNextTargetDate(Habit habit) {
        Reminder reminder = habit.getReminder();
        switch (habit.getFrequency()) {
            case DAILY -> {
                return reminder.getNextTargetDate().plusDays(habit.getDay());
            }
            case WEEKLY -> {
                return reminder.getNextTargetDate().plusWeeks(1);
            }
            case MONTHLY -> {
                return reminder.getNextTargetDate().plusMonths(1);
            }
            default -> {
                return null;
            }
        }
    }
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void targetDayBatchUpdate() {
        List<Reminder> reminders = reminderRepository.findExpired();
        reminders.forEach(this::setNextTargetDate);
        reminderRepository.saveAll(reminders);

        List<Tracker> trackers = reminders.stream().map(Reminder::getHabit).map(Habit::getTracker).toList();
        trackers.forEach(tracker -> tracker.setAllDays(tracker.getAllDays() + 1));
        trackerRepository.saveAll(trackers);
    }
}
