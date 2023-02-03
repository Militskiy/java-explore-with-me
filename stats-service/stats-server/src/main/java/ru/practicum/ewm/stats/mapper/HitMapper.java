package ru.practicum.ewm.stats.mapper;

import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.stats.model.Hit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface HitMapper {
    Hit toDocument(HitCreateRequest hitCreateRequest);

    HitCreateResponse toResponse(Hit hit);
}
