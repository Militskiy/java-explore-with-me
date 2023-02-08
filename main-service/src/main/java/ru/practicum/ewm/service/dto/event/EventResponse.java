package ru.practicum.ewm.service.dto.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.dto.location.Coordinates;
import ru.practicum.ewm.service.dto.user.Initiator;
import ru.practicum.ewm.service.model.event.EventState;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.event.Event} entity
 */
@Value
@Builder
@Jacksonized
public class EventResponse implements Serializable, Comparable<EventResponse> {
    Long id;
    String annotation;
    CategoryResponse category;
    int confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    Initiator initiator;
    Coordinates location;
    Boolean paid;
    Short participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    EventState state;
    String title;
    long views;

    @Override
    public int compareTo(EventResponse o) {
        return Long.compare(this.views, o.views);
    }
}