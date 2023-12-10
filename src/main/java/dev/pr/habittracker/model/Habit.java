package dev.pr.habittracker.model;

import dev.pr.habittracker.model.enums.Frequency;
import dev.pr.habittracker.model.enums.TimeOfDay;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Habit {
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
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false, name = "habit_frequency")
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    @Column(nullable = false, name = "habit_day")
    private int day;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @OneToOne(mappedBy = "habit", cascade = {PERSIST, REMOVE})
    private Reminder reminder;
    @OneToOne(mappedBy = "habit", cascade = {PERSIST, REMOVE})
    private Tracker tracker;
}
