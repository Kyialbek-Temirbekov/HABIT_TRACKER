package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Achievement;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Feat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AchievementRepositoryTest {
    @Autowired
    private PeopleRepository peopleRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    private Person person;

    @BeforeEach
    void setUp() {
        person = Person.builder()
                .name("Asan")
                .email("asan@gmail.com")
                .password("asan12345")
                .enabled(false)
                .build();
        this.person = peopleRepository.save(person);
        Achievement achievement = Achievement.builder()
                .title(Feat.PERFECT_STREAK.toString())
                .description(Feat.PERFECT_STREAK.getDescription())
                .person(this.person)
                .build();
        achievementRepository.save(achievement);
    }

    @Test
    void existsByAchievement() {
        Assertions.assertThat(achievementRepository.existsByAchievement(person.getId(),Feat.PERFECT_STREAK.toString())).isTrue();
    }
    @Test
    void doesNotExistsByAchievement() {
        Assertions.assertThat(achievementRepository.existsByAchievement(person.getId(),Feat.MASTER_OF_PERSISTENCE.toString())).isFalse();
    }

    @Test
    void existByPersonId() {
        Assertions.assertThat(achievementRepository.findByPersonId(person.getId())).isNotEmpty();
    }
    @Test
    void doesNotExistByPersonId() {
        Assertions.assertThat(achievementRepository.findByPersonId("0d0000f0-c00b-00d0-0fa0-000b00aca0cc")).isEmpty();
    }
}