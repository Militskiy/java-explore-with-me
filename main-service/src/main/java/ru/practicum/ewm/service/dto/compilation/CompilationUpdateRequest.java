package ru.practicum.ewm.service.dto.compilation;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.compilation.Compilation} entity
 */
@Value
@Builder
@Jacksonized
public class CompilationUpdateRequest implements Serializable {
    Set<Long> events;
    Boolean pinned;
    String title;
}