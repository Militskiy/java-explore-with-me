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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.dto.request.ParticipationRequestResponse;
import ru.practicum.ewm.service.service.request.ParticipationRequestService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Private: Participation requests")
@Validated
public class ParticipationRequestPrivateController {
    private final ParticipationRequestService service;

    @PostMapping
    @Operation(summary = "Create participation request")
    public ResponseEntity<ParticipationRequestResponse> createRequest(
            @PathVariable @Positive Long userId,
            @RequestParam @Positive Long eventId
    ) {
        log.info("Creating participation event from user with id={} for event with id={}", userId, eventId);
        return ResponseEntity.status(201).body(service.createRequest(userId, eventId));
    }

    @GetMapping
    @Operation(summary = "Get user participation requests")
    public ResponseEntity<ParticipationRequestList> findUserRequests(@PathVariable @Positive Long userId) {
        log.info("Getting participation requests for user with id={}", userId);
        return ResponseEntity.ok(service.findUserRequests(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    @Operation(summary = "Cancel user participation request")
    public ResponseEntity<ParticipationRequestResponse> cancelRequest(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long requestId
    ) {
        log.info("Canceling request with id={} for user with id={}", requestId, userId);
        return ResponseEntity.ok(service.cancelRequest(userId, requestId));
    }
}
