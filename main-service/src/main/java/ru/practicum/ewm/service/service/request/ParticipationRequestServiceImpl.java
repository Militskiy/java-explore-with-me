package ru.practicum.ewm.service.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.dto.request.ParticipationRequestMapper;
import ru.practicum.ewm.service.dto.request.ParticipationRequestResponse;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationStatusUpdateResponse;
import ru.practicum.ewm.service.dto.request.ParticipationUpdateStatus;
import ru.practicum.ewm.service.exception.ConflictException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.event.Event;
import ru.practicum.ewm.service.model.request.ParticipationRequest;
import ru.practicum.ewm.service.model.request.RequestStatus;
import ru.practicum.ewm.service.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.service.event.EventService;
import ru.practicum.ewm.service.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.service.model.event.EventState.PUBLISHED;
import static ru.practicum.ewm.service.model.request.RequestStatus.CANCELED;
import static ru.practicum.ewm.service.model.request.RequestStatus.CONFIRMED;
import static ru.practicum.ewm.service.model.request.RequestStatus.PENDING;
import static ru.practicum.ewm.service.model.request.RequestStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;
    private final ParticipationRequestMapper mapper;

    @Override
    @Transactional
    public ParticipationRequestResponse createRequest(Long userId, Long eventId) {
        var event = eventService.findEventEntity(eventId);
        if (!event.getInitiator().getId().equals(userId) &&
                event.getState().equals(PUBLISHED) &&
                event.getParticipantLimit() > 0) {
            RequestStatus status;
            if (event.getRequestModeration()) {
                status = PENDING;
            } else {
                status = CONFIRMED;
                event.setParticipantLimit(event.getParticipantLimit() - 1);
            }
            ParticipationRequest newRequest = ParticipationRequest.builder()
                    .requester(userService.findUserEntity(userId))
                    .event(event)
                    .status(status)
                    .build();
            return mapper.toResponse(requestRepository.save(newRequest));
        } else {
            throw new ConflictException("Cannot request participation on this event");
        }
    }

    @Override
    public ParticipationRequestList findUserRequests(Long userId) {
        userService.checkUser(userId);
        return ParticipationRequestList
                .builder()
                .requests(
                        requestRepository.findParticipationRequestsByRequester_Id(userId)
                                .stream()
                                .map(mapper::toResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    @Transactional
    public ParticipationRequestResponse cancelRequest(Long userId, Long requestId) {
        userService.checkUser(userId);
        ParticipationRequest request = getRequestEntity(requestId);
        if (request.getStatus().equals(CONFIRMED)) {
            request.getEvent().setParticipantLimit(request.getEvent().getParticipantLimit() + 1);
        }
        request.setStatus(CANCELED);
        return mapper.toResponse(requestRepository.save(request));
    }


    @Override
    @Transactional
    public ParticipationStatusUpdateResponse updateRequestStatus(
            Long userId,
            Long eventId,
            ParticipationStatusUpdateRequest statusUpdateRequest
    ) {
        Event event = eventService.findUserEventEntity(eventId, userId);
        if (event.getParticipantLimit() > 0) {
            List<ParticipationRequestResponse> confirmed = new ArrayList<>();
            List<ParticipationRequestResponse> rejected = new ArrayList<>();
            requestRepository.findParticipationRequestsByIdIn(statusUpdateRequest.getRequestIds())
                    .stream()
                    .peek(request -> {
                        if (request.getStatus().equals(PENDING)) {
                            if (statusUpdateRequest.getStatus().equals(ParticipationUpdateStatus.CONFIRMED)) {
                                if (event.getParticipantLimit() > 0) {
                                    request.setStatus(CONFIRMED);
                                    event.setParticipantLimit(event.getParticipantLimit() - 1);
                                } else {
                                    request.setStatus(REJECTED);
                                }
                            } else {
                                request.setStatus(REJECTED);
                            }
                        } else {
                            throw new ConflictException("Can only confirm PENDING requests");
                        }
                    })
                    .map(mapper::toResponse)
                    .forEach(request -> {
                        if (request.getStatus().equals(CONFIRMED)) {
                            confirmed.add(request);
                        } else {
                            rejected.add(request);
                        }
                    });
            return ParticipationStatusUpdateResponse.builder()
                    .confirmedRequests(confirmed)
                    .rejectedRequests(rejected)
                    .build();
        } else {
            throw new ConflictException("The participant limit has been reached");
        }
    }

    private ParticipationRequest getRequestEntity(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(
                () -> new NotFoundException("Request with id=" + requestId + " was not found")
        );
    }
}
