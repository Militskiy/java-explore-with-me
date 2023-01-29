package ru.practicum.ewm.dto;

import ru.practicum.ewm.dtos.HitCreateRequest;
import ru.practicum.ewm.dtos.HitCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.model.Hit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HitMapper {
    Hit toDocument(HitCreateRequest hitCreateRequest);

    HitCreateResponse toResponse(Hit hit);
}
