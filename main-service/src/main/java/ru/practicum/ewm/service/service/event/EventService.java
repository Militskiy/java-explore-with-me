package ru.practicum.ewm.service.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.event.EventCreateRequest;
import ru.practicum.ewm.service.dto.event.EventFullList;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateAdminRequest;
import ru.practicum.ewm.service.dto.event.EventUpdateRequest;
import ru.practicum.ewm.service.model.event.EventState;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public interface EventService {
    EventResponse createEvent(EventCreateRequest createRequest, Long userId);

    EventList findUserEvents(Long userId, Integer from, Integer size);

    EventResponse findUserEvent(Long eventId, Long userId);

    EventResponse updateEvent(EventUpdateRequest updateRequest, Long eventId, Long userId);

    EventFullList findEventsAdmin(
            Collection<Long> users,
            Collection<EventState> states,
            Collection<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size
    );

    EventResponse confirmEvent(EventUpdateAdminRequest updateAdminRequest, Long eventId);
}
