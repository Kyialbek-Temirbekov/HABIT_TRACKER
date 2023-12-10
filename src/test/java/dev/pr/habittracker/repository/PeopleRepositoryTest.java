package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PeopleRepositoryTest {
    @Autowired
    private PeopleRepository peopleRepository;
    private Person person;

    @BeforeEach
    public void setUp() {
        person = Person.builder()
                .name("Asan")
                .email("asan@gmail.com")
                .password("asan12345")
                .enabled(false)
                .build();
        this.person = peopleRepository.save(person);
    }

    @Test
    void doesNotExistsByEmail() {
        boolean exists = peopleRepository.existsByEmail("esen@gmail.com");
        assertThat(exists).isFalse();
    }
    @Test
    void doesNotExistsByEmailEnabled() {
        boolean exists = peopleRepository.existsByEmail("asan@gmail.com");
        assertThat(exists).isFalse();

    }
    @Test
    void existsByEmailEnabled() {
        person.setEnabled(true);
        peopleRepository.save(person);
        boolean exists = peopleRepository.existsByEmail("asan@gmail.com");
        assertThat(exists).isTrue();
    }
}