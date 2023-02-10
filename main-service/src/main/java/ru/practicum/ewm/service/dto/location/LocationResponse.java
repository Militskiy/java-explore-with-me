package ru.practicum.ewm.service.dto.location;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.location.Location} entity
 */
@Value
@Builder
@Jacksonized
public class LocationResponse implements Serializable {
    Long id;
    String name;
    Coordinates location;
    Long range;
}