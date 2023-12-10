package dev.pr.habittracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {
    @Id
    private String habitId;
    @Column(nullable = false)
    private LocalDate nextTargetDate;
    @Column(nullable = false)
    private boolean marked;
    @OneToOne
    @JoinColumn(name = "habit_id")
    @MapsId
    private Habit habit;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
}
