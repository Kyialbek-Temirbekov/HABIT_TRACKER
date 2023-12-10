package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.repository.PeopleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {
    private PasswordEncoder passwordEncoder;
    private PeopleService peopleService;
    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        peopleService = new PeopleService(peopleRepository, passwordEncoder, notificationService);
    }

    @Test
    void canSavePerson() {
        Person person = generatePerson();
        peopleService.save(person);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canHashAndSavePerson() {
        Person person = generatePerson();
        peopleService.hashAndSave(person);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canSignUp() {
        Person person = generatePerson();
        peopleService.signUp(person);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canSaveAndSendCode() {
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));
        peopleService.sendCode(person.getId());

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canFindAll() {
        peopleService.findAll();
        verify(peopleRepository).findAll();
    }

    @Test
    void willThrowWhenPersonNotFound() {
        String personId = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.findById(any())).willReturn(Optional.empty());

        assertThatThrownBy(() -> peopleService.findOne(personId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("%s not found with the given input data %s: %s", "Person","personId",personId));
    }

    @Test
    void canFindOneByEmail() {
        String email = "asan@gmail.com";
        peopleService.findOneByEmail(email);

        verify(peopleRepository).findByEmail(any());
    }

    @Test
    void canUpdateName() {
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));
        peopleService.updateName(person.getId(), "NEW NAME");

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canDelete() {
        String personId = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.existsById(any())).willReturn(true);

        peopleService.delete(personId);

        ArgumentCaptor<String> personIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(peopleRepository).deleteById(personIdArgumentCaptor.capture());

        String capturedPersonId = personIdArgumentCaptor.getValue();
        Assertions.assertThat(capturedPersonId).isEqualTo(personId);
    }

    @Test
    void canVerifyExistenceByEmail() {
        peopleService.existByEmail("asan@gmail.com");
        verify(peopleRepository).existsByEmail(any());
    }

    @Test
    void willThrowWhenPersonNotFoundBeforeDeleting() {
        String personId = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        given(peopleRepository.existsById(any())).willReturn(false);

        assertThatThrownBy(() -> peopleService.delete(personId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("%s not found with the given input data %s: %s", "Person","personId",personId));

        verify(peopleRepository,never()).deleteById(any());
    }

    private Person generatePerson() {
        return Person.builder()
                .name("Asan")
                .email("asan@gmail.com")
                .password("asan12345")
                .enabled(true)
                .build();
    }
}