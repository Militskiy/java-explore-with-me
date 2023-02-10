package ru.practicum.ewm.service.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.model.request.ParticipationRequest;
import ru.practicum.ewm.service.model.request.RequestStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ParticipationRequest} entity
 */
@Value
@Builder
@Jacksonized
public class ParticipationRequestResponse implements Serializable {
    Long id;
    LocalDateTime created;
    Long event;
    Long requester;
    RequestStatus status;
}