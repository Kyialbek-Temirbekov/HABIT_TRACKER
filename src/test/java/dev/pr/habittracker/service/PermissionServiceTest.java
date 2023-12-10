package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Role;
import dev.pr.habittracker.repository.PeopleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {
    private PermissionService permissionService;
    @Mock
    private PeopleRepository peopleRepository;

    @BeforeEach
    void setUp() {
        permissionService = new PermissionService(peopleRepository);
    }

    @Test
    void canAuthorizeWithRole() {
        Role role = Role.MANAGER;
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));

        permissionService.authorizeWithRole(role, id);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canDisallowWithoutRole() {
        Role role = Role.COPYWRITER;
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));

        permissionService.disallowWithoutRole(role, id);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canLock() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));

        permissionService.lock(id);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void canUnlock() {
        String id = "0d0000f0-c00b-00d0-0fa0-000b00aca0cc";
        Person person = generatePerson();
        given(peopleRepository.findById(any())).willReturn(Optional.of(person));

        permissionService.unlock(id);

        ArgumentCaptor<Person> personArgumentCaptor = ArgumentCaptor.forClass(Person.class);
        verify(peopleRepository).save(personArgumentCaptor.capture());

        Person capturedPerson = personArgumentCaptor.getValue();
        Assertions.assertThat(capturedPerson).isEqualTo(person);
    }

    @Test
    void cannotAuthorizeAsAdmin() {
        assertThatThrownBy(() -> permissionService.authorizeWithRole(Role.ADMIN, "0d0000f0-c00b-00d0-0fa0-000b00aca0cc"))
                .isInstanceOf(NotCreatedException.class)
                .hasMessageContaining("Authorization as an admin is not available or accessible");
    }

    @Test
    void cannotDisallowRoleAdmin() {
        assertThatThrownBy(() -> permissionService.disallowWithoutRole(Role.ADMIN, "0d0000f0-c00b-00d0-0fa0-000b00aca0cc"))
                .isInstanceOf(NotCreatedException.class)
                .hasMessageContaining("Disallow role admin is not available or accessible");
    }

    private Person generatePerson() {
        return Person.builder()
                .roles(new ArrayList<>(Collections.singletonList(Role.COPYWRITER)))
                .build();
    }
}