package dev.pr.habittracker.model;

import dev.pr.habittracker.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Person {
    @Id
    @UuidGenerator
    private String id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String email;
    private String password;
    private String token;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false)
    private boolean nonLocked;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToMany(mappedBy = "person", cascade = {REMOVE})
    private List<Habit> habits;
    @OneToMany(mappedBy = "person", cascade = {REMOVE})
    private List<Achievement> achievements;
    @OneToMany(mappedBy = "person")
    private List<Reminder> reminders;

}
