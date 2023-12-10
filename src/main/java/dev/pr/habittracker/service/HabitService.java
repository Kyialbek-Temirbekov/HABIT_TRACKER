package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.Tracker;
import dev.pr.habittracker.model.enums.TimeOfDay;
import dev.pr.habittracker.repository.HabitRepository;
import dev.pr.habittracker.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final PeopleRepository peopleRepository;
    private final ReminderService reminderService;
    private final TrackerService trackerService;
    private final AchievementService achievementService;
    public Habit save(Habit habit, String personId) {
        Person person = peopleRepository.findById(personId).orElseThrow(() -> new NotFoundException("Person","personId",personId));
        habit.setPerson(person);
        habit.setReminder(reminderService.produce(habit));
        habit.setTracker(trackerService.produce(habit));
        return habitRepository.save(habit);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<Habit> findAll() {
        return habitRepository.findAll();
    }
    public List<Habit> findByPersonId(String id) {
        if(!peopleRepository.existsById(id))
            throw new NotFoundException("Person","personId",id);
        return habitRepository.findByPersonId(id);
    }
    public List<Habit> findActiveHabitsByPersonId(String id) {
        if(!peopleRepository.existsById(id))
            throw new NotFoundException("Person","personId",id);
        return habitRepository.findActiveHabitsByPersonId(id).stream().filter(this::isActive).toList();
    }

    private boolean isActive(Habit habit) {
        for(TimeOfDay timeOfDay : habit.getGoal()) {
            if(timeOfDay.equals(TimeOfDay.ALL_DAY))
                return true;
            if((LocalTime.now().isAfter(LocalTime.of(timeOfDay.getStartTime(), 0)) ||
                    LocalTime.now().equals(LocalTime.of(timeOfDay.getStartTime(), 0)))
                    && LocalTime.now().isBefore(LocalTime.of(timeOfDay.getEndTime(), 0)))
                return true;
        }
        return false;
    }

    public Habit findOne(String id) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new NotFoundException("Habit","habitId",id));
        Hibernate.initialize(habit.getTracker());
        return habit;
    }
    public Habit update(String id, Habit habit) {
        if(!habitRepository.existsById(id))
            throw new NotFoundException("Habit","habitId",id);
        habit.setId(id);
        return habitRepository.save(habit);
    }
    public void delete(String id) {
        if(!habitRepository.existsById(id))
            throw new NotFoundException("Habit","habitId",id);
        habitRepository.deleteById(id);
    }
    public void checkOffHabit(String id) {
        Habit habit = habitRepository.findById(id).orElseThrow(() -> new NotFoundException("Habit","habitId",id));
        Tracker tracker = habit.getTracker();
        tracker.setCompletedDays(tracker.getCompletedDays()+1);
        habit.getReminder().setMarked(true);

        if(reminderService.getNextTargetDate(habit).isAfter(habit.getEndDate())) {
            tracker.setFinished(true);
            achievementService.create(tracker);
        }
        habitRepository.save(habit);
    }
}
