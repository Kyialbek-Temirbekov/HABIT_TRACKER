package dev.pr.habittracker.service;

import dev.pr.habittracker.model.Habit;
import dev.pr.habittracker.model.Tracker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TrackerService {
    public Tracker produce(Habit habit) {
        return Tracker.builder()
                .finished(false)
                .habit(habit)
                .build();
    }
}
