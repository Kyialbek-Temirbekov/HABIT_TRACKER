package dev.pr.habittracker.validation.validator;

import dev.pr.habittracker.dto.TermDto;
import dev.pr.habittracker.validation.constraint.ValidTerm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TermValidator implements ConstraintValidator<ValidTerm, TermDto> {
    @Override
    public void initialize(ValidTerm constraintAnnotation) {
    }

    @Override
    public boolean isValid(TermDto term, ConstraintValidatorContext context) {
        return term.getStartDate().isBefore(term.getEndDate());
    }
}
