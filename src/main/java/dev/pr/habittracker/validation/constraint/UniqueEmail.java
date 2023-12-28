package dev.pr.habittracker.validation.constraint;

import dev.pr.habittracker.validation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "user with this email already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
