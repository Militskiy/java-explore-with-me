package ru.practicum.ewm.service.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ParticipationStatusUpdateRequest {
    List<Long> requestIds;
    ParticipationUpdateStatus status;
}
