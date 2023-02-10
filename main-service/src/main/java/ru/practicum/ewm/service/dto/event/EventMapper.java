package ru.practicum.ewm.service.dto.event;

import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
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
        componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, LocationMapper.class}
)
public interface EventMapper {
    @Mapping(source = "category", target = "category.id")
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    Event toEntity(EventCreateRequest eventCreateRequest, @Context GeometryFactory factory);

    @Mapping(source = "location.y", target = "location.lat")
    @Mapping(source = "location.x", target = "location.lon")
    EventResponse toResponse(Event event);

    @Mapping(target = "views", source = "views")
    @Mapping(source = "event.location.y", target = "location.lat")
    @Mapping(source = "event.location.x", target = "location.lon")
    EventResponse toResponseWithViews(Event event, long views);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id")
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    Event updateEntity(
            EventUpdateRequest eventUpdateRequest,
            @MappingTarget Event event,
            @Context GeometryFactory factory
    );

    EventShortResponse toShortResponse(Event event);

    @Mapping(target = "views", source = "views")
    EventShortResponse toShortResponseWithViews(Event event, long views);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "category", target = "category.id")
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    Event adminUpdate(
            EventUpdateAdminRequest eventUpdateAdminRequest,
            @MappingTarget Event event, @Context GeometryFactory factory
    );
}