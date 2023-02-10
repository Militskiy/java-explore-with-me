package ru.practicum.ewm.service.dto.user;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.model.user.User;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Value
@Builder
@Jacksonized
public class UserResponse implements Serializable {
    Long id;
    String email;
    String name;
}