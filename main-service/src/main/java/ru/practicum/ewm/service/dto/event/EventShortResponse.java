package ru.practicum.ewm.service.dto.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.dto.user.Initiator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.event.Event} entity
 */
@Value
@Builder
@Jacksonized
public class EventShortResponse implements Serializable, Comparable<EventShortResponse> {
    Long id;
    String annotation;
    CategoryResponse category;
    int confirmedRequests;
    String description;
    LocalDateTime eventDate;
    Initiator initiator;
    Boolean paid;
    String title;
    long views;

    @Override
    public int compareTo(EventShortResponse o) {
        return Long.compare(this.views, o.views);
    }
}