package dev.pr.habittracker.repository;

import dev.pr.habittracker.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, String> {
}
