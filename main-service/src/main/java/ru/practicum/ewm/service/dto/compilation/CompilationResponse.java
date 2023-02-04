package ru.practicum.ewm.service.dto.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.dto.event.EventShortResponse;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.compilation.Compilation} entity
 */
@Value
@Builder
@Jacksonized
public class CompilationResponse implements Serializable {
    Long id;
    Set<EventShortResponse> events;
    Boolean pinned;
    String title;
}