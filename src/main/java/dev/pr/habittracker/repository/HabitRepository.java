package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, String> {
    @Query(value = "from Habit h where h.person.id = ?1")
    List<Habit> findByPersonId(String id);
    @Query(value = "from Habit h where h.person.id = ?1 and h.reminder.nextTargetDate = current date ")
    List<Habit> findActiveHabitsByPersonId(String id);
}
