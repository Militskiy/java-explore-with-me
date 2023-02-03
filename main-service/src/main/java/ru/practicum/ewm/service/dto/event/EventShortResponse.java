package ru.practicum.ewm.service.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.dto.user.Initiator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.event.Event} entity
 */
@AllArgsConstructor
@Getter
public class EventShortResponse implements Serializable {
    private final Long id;
    private final String annotation;
    private final CategoryResponse category;
    private final int confirmedRequests;
    private final String description;
    private final LocalDateTime eventDate;
    private final Initiator initiator;
    private final Boolean paid;
    private final String title;
    private final long views;
}