package ru.practicum.ewm.service.exception;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
    public NotFoundException(String s) {
        super(s);
    }
}
