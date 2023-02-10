package ru.practicum.ewm.service.exception;

import javax.validation.ConstraintDeclarationException;

public class EventDateException extends ConstraintDeclarationException {
    public EventDateException(String message) {
        super(message);
    }
}
