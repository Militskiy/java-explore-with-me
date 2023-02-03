package ru.practicum.ewm.service.service.event;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.event.EventCreateRequest;
import ru.practicum.ewm.service.dto.event.EventFullList;
import ru.practicum.ewm.service.dto.event.EventList;
import ru.practicum.ewm.service.dto.event.EventMapper;
import ru.practicum.ewm.service.dto.event.EventResponse;
import ru.practicum.ewm.service.dto.event.EventShortResponse;
import ru.practicum.ewm.service.dto.event.EventUpdateAdminRequest;
import ru.practicum.ewm.service.dto.event.EventUpdateRequest;
import ru.practicum.ewm.service.exception.ConflictException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.category.Category;
import ru.practicum.ewm.service.model.event.Event;
import ru.practicum.ewm.service.model.event.EventState;
import ru.practicum.ewm.service.model.event.QEvent;
import ru.practicum.ewm.service.model.user.User;
import ru.practicum.ewm.service.repository.EventRepository;
import ru.practicum.ewm.service.service.category.CategoryService;
import ru.practicum.ewm.service.service.stats.StatisticsService;
import ru.practicum.ewm.service.service.user.UserService;
import ru.practicum.ewm.stats.dto.HitStats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final EventMapper mapper;

    @Override
    @Transactional
    public EventResponse createEvent(EventCreateRequest createRequest, Long userId) {
        User user = userService.findUserEntity(userId);
        Category category = categoryService.findCategoryEntity(createRequest.getCategory());
        Event createdEvent = mapper.toEntity(createRequest);
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
            mapper.updateEntity(updateRequest, foundEvent);
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
    public EventFullList findEventsAdmin(
            Collection<Long> users,
            Collection<EventState> states,
            Collection<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Integer from,
            Integer size) {
        BooleanBuilder builder = new BooleanBuilder();
        if (users != null && !users.isEmpty()) {
            builder.and(QEvent.event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            builder.and(QEvent.event.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            builder.and(QEvent.event.category.id.in(categories));
        }
        if (rangeStart != null) {
            builder.and(QEvent.event.eventDate.after(rangeStart));
        }
        if (rangeEnd != null) {
            builder.and(QEvent.event.eventDate.before(rangeEnd));
        }
        List<Event> result = eventRepository.findAll(builder, PageRequest.of(from, size)).toList();
        return EventFullList.builder().events(buildEventResponse(result)).build();
    }

    @Override
    public EventResponse confirmEvent(EventUpdateAdminRequest updateAdminRequest, Long eventId) {
        Event foundEvent = findEventEntity(eventId);
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
            mapper.adminUpdate(updateAdminRequest, foundEvent);
            return mapper.toResponse(eventRepository.save(foundEvent));
        }
        throw new ConflictException("Cannot update the event because it's not in the right state: " +
                foundEvent.getState());
    }

    private Event findEventEntity(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
    }

    private Event findUserEventEntity(Long eventId, Long userId) {
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
        var stats = buildStatMap(eventList);
        if (stats != null && !stats.isEmpty()) {
            return eventList.stream()
                    .map(event -> stats.containsKey(event.getId()) ?
                            mapper.toShortResponseWithViews(event, stats.get(event.getId())) :
                            mapper.toShortResponse(event))
                    .collect(Collectors.toList());
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
}
