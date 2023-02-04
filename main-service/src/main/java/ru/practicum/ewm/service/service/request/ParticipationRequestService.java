package ru.practicum.ewm.service.service.request;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.dto.request.ParticipationRequestResponse;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateResponse;

@Service
public interface ParticipationRequestService {
    ParticipationRequestResponse createRequest(Long userId, Long eventId);

    ParticipationRequestList findUserRequests(Long userId);

    ParticipationRequestResponse cancelRequest(Long userId, Long requestId);

    ParticipationStatusUpdateResponse updateRequestStatus(
            Long userId, Long eventId, ParticipationStatusUpdateRequest updateRequest
    );
}
