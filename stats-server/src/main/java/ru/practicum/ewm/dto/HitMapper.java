package ru.practicum.ewm.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.model.Hit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HitMapper {
    Hit toDocument(HitCreateRequest hitCreateRequest);

    HitCreateResponse toResponseFromMongo(Hit hit);
}
