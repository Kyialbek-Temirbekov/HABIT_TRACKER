package dev.pr.habittracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Contact {
    @Id
    @UuidGenerator
    private String id;
    @Column(nullable = false)
    private String email;
    private String subject;
    @Column(nullable = false, length = 1000)
    private String message;
    @Column(nullable = false)
    private LocalDate createDt;
}
