package ru.practicum.ewm.service.service.event;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.event.EventCreateRequest;
import ru.practicum.ewm.service.dto.event.EventFilter;
import ru.practicum.ewm.service.dto.event.EventFullList;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventMapper;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventShortResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateAdminRequest;
import ru.practicum.ewm.service.dto.event.EventUpdateRequest;
import ru.practicum.ewm.service.dto.request.ParticipationRequestList;
import ru.practicum.ewm.service.dto.request.ParticipationRequestMapper;
import ru.practicum.ewm.service.exception.ConflictException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.category.Category;
import ru.practicum.ewm.service.model.event.Event;
import ru.practicum.ewm.service.model.event.QEvent;
import ru.practicum.ewm.service.model.location.Location;
import ru.practicum.ewm.service.model.user.User;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.service.category.CategoryService;
import ru.practicum.ewm.service.service.location.LocationService;
import ru.practicum.ewm.service.service.stats.StatisticsService;
import ru.practicum.ewm.service.service.user.UserService;
import ru.practicum.ewm.stats.dto.HitStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.ewm.service.dto.event.SortType.VIEWS;
import static ru.practicum.ewm.service.model.event.AdminStateAction.PUBLISH_EVENT;
import static ru.practicum.ewm.service.model.event.AdminStateAction.REJECT_EVENT;
import static ru.practicum.ewm.service.model.event.EventState.CANCELED;
import static ru.practicum.ewm.service.model.event.EventState.PENDING;
import static ru.practicum.ewm.service.model.event.EventState.PUBLISHED;
import static ru.practicum.ewm.service.model.event.UserStateActions.CANCEL_REVIEW;
import static ru.practicum.ewm.service.model.event.UserStateActions.SEND_TO_REVIEW;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final StatisticsService statisticsService;
    private final LocationService locationService;
    private final EventMapper mapper;
    private final ParticipationRequestMapper participationRequestMapper;
    private final GeometryFactory factory;


    @Override
    @Transactional
    public EventResponse createEvent(EventCreateRequest createRequest, Long userId) {
        User user = userService.findUserEntity(userId);
        Category category = categoryService.findCategoryEntity(createRequest.getCategory());
        Event createdEvent = mapper.toEntity(createRequest, factory);
        createdEvent.setInitiator(user);
        createdEvent.setCategory(category);
        return mapper.toResponse(eventRepository.save(createdEvent));
    }

    @Override
    public EventList findUserEvents(Long userId, Integer from, Integer size) {
        userService.findUserEntity(userId);
        List<Event> events = eventRepository.findEventsByInitiator_Id(userId, PageRequest.of(from, size));
        if (!events.isEmpty()) {
            return EventList.builder()
                    .events(buildEventShortResponse(events))
                    .build();
        } else {
            return EventList.builder().events(new ArrayList<>()).build();
        }
    }

    @Override
    public EventResponse findUserEvent(Long eventId, Long userId) {
        Event foundEvent = findUserEventEntity(eventId, userId);
        return buildEventResponse(List.of(foundEvent)).get(0);
    }

    @Override
    @Transactional
    public EventResponse updateEvent(EventUpdateRequest updateRequest, Long eventId, Long userId) {
        Event foundEvent = findUserEventEntity(eventId, userId);
        if (!foundEvent.getState().equals(PUBLISHED)) {
            mapper.updateEntity(updateRequest, foundEvent, factory);
            if (updateRequest.getStateAction() != null) {
                if (updateRequest.getStateAction().equals(SEND_TO_REVIEW)) {
                    foundEvent.setState(PENDING);
                }
                if (updateRequest.getStateAction().equals(CANCEL_REVIEW)) {
                    foundEvent.setState(CANCELED);
                }
            }
            if (updateRequest.getCategory() != null) {
                Category category = categoryService.findCategoryEntity(updateRequest.getCategory());
                foundEvent.setCategory(category);
            }
            return mapper.toResponse(eventRepository.save(foundEvent));
        }
        throw new ConflictException("Only pending or canceled events can be changed");
    }

    @Override
    public EventFullList findEventsAdmin(EventFilter filter, Integer from, Integer size) {
        List<Event> result = eventRepository.findAll(buildQuery(filter), PageRequest.of(from, size)).toList();
        return EventFullList.builder().events(buildEventResponse(result)).build();
    }

    @Override
    public EventList findEventsPublic(EventFilter filter, Integer from, Integer size) {
        if (filter.getOnlyAvailable() == null) {
            filter = filter.toBuilder().onlyAvailable(Boolean.FALSE).build();
        }
        var queryBuilder = buildQuery(filter);
        if (filter.getRangeStart() == null && filter.getRangeEnd() == null) {
            queryBuilder.and(QEvent.event.eventDate.after(LocalDateTime.now()));
        }
        var result = buildEventShortResponse(eventRepository.findAll(
                queryBuilder,
                PageRequest.of(from, size, Sort.by("eventDate").ascending())
        ).toList());

        if (filter.getSort() != null && filter.getSort().equals(VIEWS)) {
            result.sort(EventShortResponse::compareTo);
        }
        return EventList.builder().events(result).build();
    }

    @Override
    @Transactional
    public EventResponse confirmEvent(EventUpdateAdminRequest updateAdminRequest, Long eventId) {
        var foundEvent = findEventEntity(eventId);
        if (!foundEvent.getState().equals(PUBLISHED)) {
            if (updateAdminRequest.getStateAction() != null) {
                if (updateAdminRequest.getStateAction().equals(PUBLISH_EVENT) &&
                        foundEvent.getState().equals(PENDING)) {
                    foundEvent.setState(PUBLISHED);
                } else if (updateAdminRequest.getStateAction().equals(REJECT_EVENT)) {
                    foundEvent.setState(CANCELED);
                } else {
                    throw new ConflictException("Cannot update the event because it's not in the right state: " +
                            foundEvent.getState());
                }
            }
            if (updateAdminRequest.getCategory() != null) {
                var category = categoryService.findCategoryEntity(updateAdminRequest.getCategory());
                foundEvent.setCategory(category);
            }
            mapper.adminUpdate(updateAdminRequest, foundEvent, factory);
            return mapper.toResponse(eventRepository.save(foundEvent));
        }
        throw new ConflictException("Cannot update the event because it's not in the right state: " +
                foundEvent.getState());
    }

    @Override
    public Event findEventEntity(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    @Override
    public ParticipationRequestList findUserEventRequests(Long userId, Long eventId) {
        return ParticipationRequestList.builder().requests(
                eventRepository.findByIdAndInitiator_Id(eventId, userId).orElseThrow(
                                () -> new NotFoundException("Event with id=" + eventId + " was not found")
                        )
                        .getRequests().stream()
                        .map(participationRequestMapper::toResponse)
                        .collect(Collectors.toList())
        ).build();
    }

    @Override
    public EventResponse findEvent(Long eventId) {
        return buildEventResponse(List.of(findEventEntity(eventId))).get(0);
    }

    @Override
    public Set<Event> findEventsFrom(Set<Long> eventIds) {
        return eventRepository.findEventsByIdIn(eventIds);
    }

    @Override
    public Event findUserEventEntity(Long eventId, Long userId) {
        return eventRepository.findEventByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private List<EventResponse> buildEventResponse(List<Event> eventList) {
        if (!eventList.isEmpty()) {
            var stats = buildStatMap(eventList);
            if (stats != null && !stats.isEmpty()) {
                return eventList.stream()
                        .map(event -> stats.containsKey(event.getId()) ?
                                mapper.toResponseWithViews(event, stats.get(event.getId())) :
                                mapper.toResponse(event))
                        .collect(Collectors.toList());
            }
        }
        return eventList.stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    private List<EventShortResponse> buildEventShortResponse(List<Event> eventList) {
        if (!eventList.isEmpty()) {
            var stats = buildStatMap(eventList);
            if (stats != null && !stats.isEmpty()) {
                return eventList.stream()
                        .map(event -> stats.containsKey(event.getId()) ?
                                mapper.toShortResponseWithViews(event, stats.get(event.getId())) :
                                mapper.toShortResponse(event))
                        .collect(Collectors.toList());
            }
        }
        return eventList.stream().map(mapper::toShortResponse).collect(Collectors.toList());
    }

    private Map<Long, Long> buildStatMap(List<Event> eventList) {
        var start = eventList.stream()
                .map(Event::getCreatedOn)
                .min(LocalDateTime::compareTo).orElse(LocalDateTime.MIN);
        var eventIdList = eventList.stream().map(Event::getId).collect(Collectors.toList());
        return statisticsService.getStats(start, eventIdList)
                .collect(Collectors.toMap(hitStats -> Long.valueOf(hitStats.getUri()
                        .substring(hitStats.getUri().length() - 1)), HitStats::getHits))
                .block();
    }

    @NonNull
    private BooleanBuilder buildQuery(@NonNull EventFilter filter) {
        BooleanBuilder builder = new BooleanBuilder();
        if (filter.getText() != null) {
            builder.and(QEvent.event.annotation.likeIgnoreCase(filter.getText())
                    .or(QEvent.event.description.likeIgnoreCase(filter.getText())));
        }
        if (filter.getUsers() != null && !filter.getUsers().isEmpty()) {
            builder.and(QEvent.event.initiator.id.in(filter.getUsers()));
        }
        if (filter.getStates() != null && !filter.getStates().isEmpty()) {
            builder.and(QEvent.event.state.in(filter.getStates()));
        }
        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            builder.and(QEvent.event.category.id.in(filter.getCategories()));
        }
        if (filter.getPaid() != null) {
            builder.and(QEvent.event.paid.eq(filter.getPaid()));
        }
        if (filter.getRangeStart() != null) {
            builder.and(QEvent.event.eventDate.after(filter.getRangeStart()));
        }
        if (filter.getRangeEnd() != null) {
            builder.and(QEvent.event.eventDate.before(filter.getRangeEnd()));
        }
        if (filter.getOnlyAvailable() != null) {
            builder.and(QEvent.event.participantLimit.eq(0)
                    .or(QEvent.event.participantLimit.gt(QEvent.event.confirmedRequests)));
        }
        if (filter.getLocation() != null) {
            Location centerPoint = locationService.getLocationEntity(filter.getLocation());
            builder.and(Expressions.numberTemplate(Double.class, "st_distancesphere({0}, {1})",
                    QEvent.event.location, centerPoint.getLocation()).loe(centerPoint.getRange()));
        }
        return builder;
    }
}
