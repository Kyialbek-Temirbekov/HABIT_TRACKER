package dev.pr.habittracker.controller;

import dev.pr.habittracker.dto.*;
import dev.pr.habittracker.exception.NotCreatedException;
import dev.pr.habittracker.model.Person;
import dev.pr.habittracker.model.enums.Role;
import dev.pr.habittracker.service.PeopleService;
import dev.pr.habittracker.util.EmailValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Validated
public class PersonController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @PostMapping("/signUp")
    public ResponseEntity<PersonDto> signUp(@RequestBody @Valid PersonDto personDto) {
        Person person = peopleService.signUp(convertToPerson(personDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToPersonDto(person));
    }
    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@RequestBody TokenRequest token, @PathVariable("id") String id) {
        Person person = peopleService.findOne(id);
        verifyToken(person, token.getToken());
        person.setEnabled(true);
        person.setNonLocked(true);
        person.getRoles().add(Role.USER);
        peopleService.save(person);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageDto("Email verified successfully"));
    }
    @PatchMapping("/forgotPassword/{id}")
    public ResponseEntity<?> forgotPassword(@PathVariable("id") String id) {
        peopleService.sendCode(id);
        return ResponseEntity.ok(new MessageDto("Verification code sent to email"));
    }
    @PatchMapping("/password/{id}")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordRequest passwordRequest, @PathVariable("id") String id) {
        Person person = peopleService.findOne(id);
        verifyToken(person, passwordRequest.getToken());
        person.setPassword(passwordRequest.getPassword());
        peopleService.hashAndSave(person);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageDto("Password reset successfully"));
    }
    @GetMapping("/reviewAll")
    public List<PersonDto> review() {
        return peopleService.findAll().stream().map(this::convertToPersonDto).toList();
    }
    @GetMapping("/review/{id}")
    public ResponseEntity<PersonDto> review(@PathVariable("id") String id) {
        return ResponseEntity.ok(convertToPersonDto(peopleService.findOne(id)));
    }
    @PatchMapping("/name/{id}")
    public ResponseEntity<?> updateName(@PathVariable("id") String id, @RequestBody @Valid NameRequest simpleRequest) {
        Person person = peopleService.updateName(id, simpleRequest.getName());
        return ResponseEntity.ok(convertToPersonDto(person));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        peopleService.delete(id);
        return ResponseEntity.ok(new MessageDto("User deleted successfully"));
    }
    @GetMapping()
    public ResponseEntity<PersonDto> getUserDetailsAfterLogin(Authentication auth) {
        Optional<Person> person = peopleService.findOneByEmail(auth.getName());
        return person.map(value -> ResponseEntity.ok(convertToPersonDto(value))).orElse(null);
    }
    @GetMapping("/google")
    public ResponseEntity<PersonDto> getUserDetailsOrEnroll(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        if(!peopleService.existByEmail(email)) {
            Person person = Person.builder()
                    .name(name)
                    .email(email)
                    .enabled(true)
                    .nonLocked(true)
                    .roles(List.of(Role.USER))
                    .build();
            peopleService.save(person);
        }
        Optional<Person> target = peopleService.findOneByEmail(email);
        return target.map(value -> ResponseEntity.ok(convertToPersonDto(value))).orElse(null);
    }

    private PersonDto convertToPersonDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }

    private Person convertToPerson(PersonDto personDto) {
        return modelMapper.map(personDto, Person.class);
    }
    private void verifyToken(Person person, String token) {
        if(!person.getToken().equals(token))
            throw new BadCredentialsException("Invalid token received");
    }
}
