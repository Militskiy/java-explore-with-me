package ru.practicum.ewm.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.model.Hit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HitMapper {
    Hit toEntity(HitCreateRequest hitCreateRequest);

    HitCreateResponse toCreateResponse(Hit hit);
}
