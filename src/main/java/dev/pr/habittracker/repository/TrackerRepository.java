package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, String> {
}
