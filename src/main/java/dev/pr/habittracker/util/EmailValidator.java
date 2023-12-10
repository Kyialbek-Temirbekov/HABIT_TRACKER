package dev.pr.habittracker.util;

import dev.pr.habittracker.dto.PersonDto;
import dev.pr.habittracker.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class EmailValidator implements Validator {
    private final PeopleRepository peopleRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return PersonDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(supports(target.getClass())) {
            PersonDto personDto = (PersonDto) target;
            if(peopleRepository.existsByEmail(personDto.getEmail())) {
                errors.rejectValue("email", "", "user with this email already exist");
            }
        }
    }
}
