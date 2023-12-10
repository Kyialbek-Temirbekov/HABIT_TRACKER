package dev.pr.habittracker.util;

import dev.pr.habittracker.dto.HabitDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class TermValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return HabitDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(supports(target.getClass())) {
            HabitDto habitDto = (HabitDto) target;
            LocalDate startDate = habitDto.getStartDate();
            LocalDate endDate = habitDto.getEndDate();
            if(startDate.isAfter(endDate) || startDate.isEqual(endDate))
                errors.rejectValue("startDate", "", "start date should not be after or equal to end date");
        }
    }
}
