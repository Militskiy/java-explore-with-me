package ru.practicum.ewm.service.dto.event;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.service.model.event.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class EventFilter {
    String text;
    Collection<Long> users;
    Collection<EventState> states;
    Collection<Long> categories;
    Boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    SortType sort;
}
