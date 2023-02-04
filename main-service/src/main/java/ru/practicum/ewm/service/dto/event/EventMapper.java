package ru.practicum.ewm.service.dto.event;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.service.dto.category.CategoryMapper;
import ru.practicum.ewm.service.dto.location.LocationMapper;
import ru.practicum.ewm.service.dto.user.UserMapper;
import ru.practicum.ewm.service.model.event.Event;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {LocationMapper.class, CategoryMapper.class, UserMapper.class}
)
public interface EventMapper {
    @Mapping(source = "category", target = "category.id")
    Event toEntity(EventCreateRequest eventCreateRequest);

    EventResponse toResponse(Event event);

    @Mapping(target = "views", source = "views")
    EventResponse toResponseWithViews(Event event, long views);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id")
    Event updateEntity(EventUpdateRequest eventUpdateRequest, @MappingTarget Event event);

    EventShortResponse toShortResponse(Event event);

    @Mapping(target = "views", source = "views")
    EventShortResponse toShortResponseWithViews(Event event, long views);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id")
    Event adminUpdate(EventUpdateAdminRequest eventUpdateAdminRequest, @MappingTarget Event event);

    Event toEntity1(EventShortResponse eventShortResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event partialUpdate(EventShortResponse eventShortResponse, @MappingTarget Event event);
}