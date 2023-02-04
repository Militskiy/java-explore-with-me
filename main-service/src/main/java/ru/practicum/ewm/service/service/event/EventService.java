package ru.practicum.ewm.service.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.event.EventCreateRequest;
import ru.practicum.ewm.service.dto.event.EventFilter;
import ru.practicum.ewm.service.dto.event.EventFullList;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateAdminRequest;
import ru.practicum.ewm.service.dto.event.EventUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.model.event.Event;

@Service
public interface EventService {
    EventResponse createEvent(EventCreateRequest createRequest, Long userId);

    EventList findUserEvents(Long userId, Integer from, Integer size);

    EventResponse findUserEvent(Long eventId, Long userId);

    EventResponse updateEvent(EventUpdateRequest updateRequest, Long eventId, Long userId);

    EventFullList findEventsAdmin(EventFilter filter, Integer from, Integer size);

    EventList findEventsPublic(EventFilter filter, Integer from, Integer size);

    EventResponse confirmEvent(EventUpdateAdminRequest updateAdminRequest, Long eventId);

    Event findEventEntity(Long eventId);

    Event findUserEventEntity(Long eventId, Long userId);

    ParticipationRequestList findUserEventRequests(Long userId, Long eventId);
    EventResponse findEvent(Long eventId);
}
