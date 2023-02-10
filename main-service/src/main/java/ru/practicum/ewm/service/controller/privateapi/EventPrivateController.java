package ru.practicum.ewm.service.controller.privateapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.event.EventCreateRequest;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateResponse;
import ru.practicum.ewm.service.service.event.EventService;
import ru.practicum.ewm.service.service.request.ParticipationRequestService;
import ru.practicum.ewm.service.validator.ValidationSequence;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Private: Events")
@Validated
public class EventPrivateController {
    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;

    @PostMapping
    @Operation(summary = "Add new event")
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody @Validated(ValidationSequence.class) EventCreateRequest createRequest,
            @PathVariable @Positive Long userId
    ) {
        log.info("Adding new event: {}", createRequest);
        return ResponseEntity.status(201).body(eventService.createEvent(createRequest, userId));
    }

    @GetMapping
    @Operation(summary = "Find user events")
    public ResponseEntity<EventList> findUserEvents(
            @PathVariable @Positive Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size

    ) {
        log.info("Getting events for user with ID: {}", userId);
        return ResponseEntity.ok(eventService.findUserEvents(userId, from, size));
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Find user event by ID")
    public ResponseEntity<EventResponse> findUserEvent(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId
    ) {
        log.info("Getting event with id={} for user with id={}", eventId, userId);
        return ResponseEntity.ok(eventService.findUserEvent(eventId, userId));
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "Update event")
    public ResponseEntity<EventResponse> updateEvent(
            @RequestBody @Valid EventUpdateRequest updateRequest,
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId
    ) {
        log.info("Updating event with id={} for user with id={}", eventId, userId);
        return ResponseEntity.ok(eventService.updateEvent(updateRequest, eventId, userId));
    }

    @GetMapping("/{eventId}/requests")
    @Operation(summary = "Get requests for user event")
    public ResponseEntity<ParticipationRequestList> getEventRequests(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId
    ) {
        log.info("Getting participation requests for user with id={} event with id={}", userId, eventId);
        return ResponseEntity.ok(eventService.findUserEventRequests(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    @Operation(summary = "Confirm or reject participation requests")
    public ResponseEntity<ParticipationStatusUpdateResponse> updateEventRequestsStatus(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long eventId,
            @RequestBody @Valid ParticipationStatusUpdateRequest request
    ) {
        log.info("Updating following requests={} for user with id={} event with id={} ", request, userId, eventId);
        return ResponseEntity.ok(participationRequestService.updateRequestStatus(userId, eventId, request));
    }
}
