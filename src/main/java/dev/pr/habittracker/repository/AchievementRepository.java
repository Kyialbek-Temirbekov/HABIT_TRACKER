package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, String> {
    @Query(value = "select case when count(a) > 0 then true else false end " +
            "from Achievement a where a.person.id = ?1 and a.title = ?2")
    boolean existsByAchievement(String personId, String title);
    @Query(value = "from Achievement a where a.person.id = ?1")
    List<Achievement> findByPersonId(String personId);
}
