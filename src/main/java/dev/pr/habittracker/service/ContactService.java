package dev.pr.habittracker.service;

import dev.pr.habittracker.model.Contact;
import dev.pr.habittracker.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    @Async
    public void sendMessage(Contact contact) {
        contact.setCreateDt(LocalDate.now());
        contactRepository.save(contact);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Contact> getMessages() {
        return contactRepository.findAll();
    }
}
