package ru.practicum.ewm.service.annotation;

import ru.practicum.ewm.service.validator.EventDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EventDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventNotEarly {
    String message() default "Not enough time before event start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 2;
}
