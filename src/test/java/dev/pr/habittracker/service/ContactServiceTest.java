package dev.pr.habittracker.service;

import dev.pr.habittracker.model.Contact;
import dev.pr.habittracker.repository.ContactRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    private ContactService contactService;
    @Mock
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(contactRepository);
    }

    @Test
    void canSendMessage() {
        Contact contact = new Contact();
        contactService.sendMessage(contact);

        ArgumentCaptor<Contact> contactArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(contactRepository).save(contactArgumentCaptor.capture());

        Contact capturedContact = contactArgumentCaptor.getValue();
        Assertions.assertThat(capturedContact).isEqualTo(contact);
    }

    @Test
    void getMessages() {
        contactService.getMessages();
        verify(contactRepository).findAll();
    }
}