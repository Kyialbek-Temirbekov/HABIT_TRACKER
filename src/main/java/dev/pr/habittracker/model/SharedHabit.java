package dev.pr.habittracker.model;

import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedHabit {
    @Id
    @UuidGenerator
    private String id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(length = 500)
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = TimeOfDay.class, fetch = FetchType.EAGER)
    private List<TimeOfDay> goal;
    @Column(nullable = false, name = "habit_frequency")
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    @Column(nullable = false, name = "habit_day")
    private int day;
}
