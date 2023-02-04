package ru.practicum.ewm.service.controller.publicapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.event.EventFilter;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.service.event.EventService;
import ru.practicum.ewm.service.service.stats.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public: Events")
@Validated
public class EventPublicController {
    private final EventService eventService;
    private final StatisticsService statisticsService;

    @GetMapping
    @Operation(summary = "Search events")
    public ResponseEntity<EventList> findEvents(
            HttpServletRequest servlet,
            EventFilter filter,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Searching events with following parameters:{}, from={}, size={}",
                filter, from, size);
        statisticsService.saveUriHitStats(servlet);
        return ResponseEntity.ok(eventService.findEventsPublic(filter, from, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by id")
    public ResponseEntity<EventResponse> findEvent(
            HttpServletRequest servlet,
            @PathVariable @Positive Long id
    ) {
        log.info("Getting event with id={}", id);
        statisticsService.saveUriHitStats(servlet);
        return ResponseEntity.ok(eventService.findEvent(id));
    }
}
