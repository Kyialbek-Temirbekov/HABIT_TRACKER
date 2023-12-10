package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.SharedHabit;
import dev.pr.habittracker.repository.SharedHabitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SharedHabitService {
    private final SharedHabitRepository sharedHabitRepository;
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COPYWRITER')")
    public SharedHabit save(SharedHabit sharedHabit) {
        return sharedHabitRepository.save(sharedHabit);
    }
    public List<SharedHabit> findAll() {
        return sharedHabitRepository.findAll();
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','COPYWRITER')")
    public void delete(String sharedHabitId) {
        if(!sharedHabitRepository.existsById(sharedHabitId))
            throw new NotFoundException("Habit","habitId",sharedHabitId);
        sharedHabitRepository.deleteById(sharedHabitId);
    }
}
