package ru.practicum.ewm.service.dto.request;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ParticipationStatusUpdateResponse {
    List<ParticipationRequestResponse> confirmedRequests;
    List<ParticipationRequestResponse> rejectedRequests;
}
