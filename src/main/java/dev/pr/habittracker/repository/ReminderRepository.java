package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, String> {
    @Query(value = "from Reminder r join Tracker t on r.habitId = t.habitId where r.nextTargetDate < current date and t.finished = false ")
    List<Reminder> findExpired();
}
