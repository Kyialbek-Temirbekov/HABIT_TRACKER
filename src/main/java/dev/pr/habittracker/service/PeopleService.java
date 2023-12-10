package dev.pr.habittracker.service;

import dev.pr.habittracker.dto.NotificationRequest;
import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.repository.PeopleRepository;
import dev.pr.habittracker.util.NumericTokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public Person save(Person person) {
        return peopleRepository.save(person);
    }
    public Person hashAndSave(Person person) {
        String hashPwd = passwordEncoder.encode(person.getPassword());
        person.setPassword(hashPwd);
        return peopleRepository.save(person);
    }
    public Person signUp(Person person) {
        String numericToken = NumericTokenGenerator.generateToken(6);
        NotificationRequest notification = NotificationRequest.builder()
                .recipient(person.getEmail())
                .subject("Email verification")
                .text(numericToken + "\n\nThis is your verification token. Use this to sign up to Habit Tracker service.")
                .build();
        notificationService.sendNotification(notification);
        String hashPwd = passwordEncoder.encode(person.getPassword());
        person.setPassword(hashPwd);
        person.setToken(numericToken);
        person.setEnabled(false);

        return peopleRepository.save(person);
    }
    public void sendCode(String id) {
        Person person = findOne(id);
        String numericToken = NumericTokenGenerator.generateToken(6);
        person.setToken(numericToken);
        peopleRepository.save(person);
        NotificationRequest notification = NotificationRequest.builder()
                .recipient(person.getEmail())
                .subject("Password reset")
                .text(numericToken + "\n\nThis is your verification token. Use this to reset password.")
                .build();
        notificationService.sendNotification(notification);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }
    public Person findOne(String id) {
        return peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
    }
    public Optional<Person> findOneByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }
    public Person updateName(String id, String name) {
        Person person = peopleRepository.findById(id).orElseThrow(() -> new NotFoundException("Person","personId",id));
        person.setName(name);
        return peopleRepository.save(person);
    }
    public void delete(String id) {
        if(!peopleRepository.existsById(id))
            throw new NotFoundException("Person","personId",id);
        peopleRepository.deleteById(id);
    }
    public boolean existByEmail(String email) {
        return peopleRepository.existsByEmail(email);
    }
}
