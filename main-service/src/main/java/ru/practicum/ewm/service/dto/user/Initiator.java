package ru.practicum.ewm.service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.user.User} entity
 */
@AllArgsConstructor
@Getter
public class Initiator implements Serializable {
    private final Long id;
    private final String email;
}