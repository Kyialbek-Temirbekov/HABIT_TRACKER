package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.ContactDto;
import dev.pr.habittracker.dto.MessageDto;
import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.model.Contact;
import dev.pr.habittracker.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Validated
public class ContactController {
    private final ContactService contactService;
    private final ModelMapper modelMapper;
    @PostMapping()
    public ResponseEntity<?> sendMessage(@RequestBody @Valid ContactDto contactDto) {
        contactService.sendMessage(convertToContact(contactDto));
        return ResponseEntity.ok(new MessageDto("Message sent successfully"));
    }
    @GetMapping()
    public List<Contact> getContacts() {
        return contactService.getMessages();
    }

    private Contact convertToContact(ContactDto contactDto) {
        return modelMapper.map(contactDto, Contact.class);
    }
}
