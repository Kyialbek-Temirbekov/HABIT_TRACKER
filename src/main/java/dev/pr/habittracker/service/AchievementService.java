package dev.pr.habittracker.service;

import dev.pr.habittracker.exception.NotFoundException;
import dev.pr.habittracker.model.Achievement;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.Tracker;
import dev.pr.habittracker.model.enums.Feat;
import dev.pr.habittracker.repository.AchievementRepository;
import dev.pr.habittracker.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final PeopleRepository peopleRepository;
    public List<Achievement> findByPersonId(String personId) {
        if(!peopleRepository.existsById(personId))
            throw new NotFoundException("Person","personId",personId);
        return  achievementRepository.findByPersonId(personId).stream().map(this::setDescription).toList();
    }

    private Achievement  setDescription(Achievement achievement) {
        achievement.setDescription(Feat.valueOf(achievement.getTitle()).getDescription());
        return achievement;
    }

    @Async
    public void create(Tracker tracker) {
        Feat feat = null;
        Person person = tracker.getHabit().getPerson();

        if(tracker.getAllDays() == tracker.getCompletedDays()) {
            if(tracker.getAllDays() > 180) {
                feat = Feat.MASTER_OF_PERSISTENCE;
            }
            else if(tracker.getAllDays() > 30) {
                feat = Feat.FLAWLESS_MONTH;
            }
            else {
                feat = Feat.PERFECT_STREAK;
            }

        }
        else if(tracker.getAllDays() * 0.5 > tracker.getCompletedDays()) {
            feat = Feat.DILIGENT_PERFORMER;
        }

        // Creating achievement
        if(feat != null) {
            Achievement achievement = Achievement.builder()
                    .title(feat.toString())
                    .person(person)
                    .build();
            if(!achievementRepository.existsByAchievement(person.getId(), feat.toString())) {
                achievementRepository.save(achievement);
            }
        }
    }
}
