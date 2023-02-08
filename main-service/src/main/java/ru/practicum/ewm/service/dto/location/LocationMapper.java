package ru.practicum.ewm.service.dto.location;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.service.model.location.Location;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    Location toEntity(LocationCreateRequest locationCreateRequest, @Context GeometryFactory factory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    Location partialUpdate(
            LocationCreateRequest locationCreateRequest,
            @MappingTarget Location location,
            @Context GeometryFactory factory
    );

    @Mapping(source = "location.y", target = "location.lat")
    @Mapping(source = "location.x", target = "location.lon")
    LocationResponse toResponse(Location location);

    @Named(value = "toPoint")
    default Point map(Coordinates location, @Context GeometryFactory factory) {
        return factory.createPoint(new Coordinate(location.getLon(), location.getLat()));
    }
}