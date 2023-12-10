package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.SharedHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedHabitRepository extends JpaRepository<SharedHabit, String> {
}
