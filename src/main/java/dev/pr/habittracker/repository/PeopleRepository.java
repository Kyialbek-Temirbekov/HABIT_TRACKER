package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, String> {
    Optional<Person> findByEmail(String email);
    @Query(value = "select case when count(p) > 0 then true else false end from Person p where p.email = ?1 and p.enabled = true ")
    boolean existsByEmail(String email);
}
