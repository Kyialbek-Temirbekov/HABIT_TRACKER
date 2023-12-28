package dev.pr.habittracker.validation.constraint;

import dev.pr.habittracker.validation.validator.TermValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TermValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTerm {
    String message() default "start date should not be after or equal to end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
