package ru.practicum.ewm.service.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.event.EventFullList;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateAdminRequest;
import ru.practicum.ewm.service.model.event.EventState;
import ru.practicum.ewm.service.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Events")
@Validated
public class EventAdminController {
    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Admin event search")
    public ResponseEntity<EventFullList> findEvents(
            @RequestParam(required = false) Collection<Long> users,
            @RequestParam(required = false) Collection<EventState> states,
            @RequestParam(required = false) Collection<Long> categories,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Getting events with parameters: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}",
                users, states, categories, rangeEnd, rangeEnd);
        return ResponseEntity.ok(
                eventService.findEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size)
        );
    }

    @PatchMapping("/{eventId}")
    @Operation(summary = "Admin event confirmation")
    public ResponseEntity<EventResponse> confirmEvent(
            @RequestBody @Valid EventUpdateAdminRequest updateAdminRequest,
            @PathVariable @Positive Long eventId
    ) {
        log.info("Admin update for event with id={}", eventId);
        return ResponseEntity.ok(eventService.confirmEvent(updateAdminRequest, eventId));
    }
}
