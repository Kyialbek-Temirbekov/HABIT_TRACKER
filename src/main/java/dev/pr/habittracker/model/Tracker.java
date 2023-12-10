package dev.pr.habittracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tracker {
    @Id
    private String habitId;
    private int allDays;
    private int completedDays;
    @Column(nullable = false)
    private boolean finished;
    @OneToOne
    @JoinColumn(name = "habit_id")
    @MapsId
    private Habit habit;
}
