package ru.practicum.ewm.service.validator;

import ru.practicum.ewm.service.annotation.EventNotEarly;
import ru.practicum.ewm.service.exception.EventDateException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EventDateValidator implements ConstraintValidator<EventNotEarly, LocalDateTime> {
    private int value;
    private String message;

    @Override
    public void initialize(EventNotEarly constraintAnnotation) {
        value = constraintAnnotation.value();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        if (eventDate == null) {
            return true;
        }
        if (eventDate.minusHours(value).isBefore(LocalDateTime.now())) {
            throw new EventDateException(message);
        }
        return true;
    }
}
