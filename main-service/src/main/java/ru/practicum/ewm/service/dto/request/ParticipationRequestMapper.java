package ru.practicum.ewm.service.dto.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.service.model.request.ParticipationRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParticipationRequestMapper {
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "event.id", target = "event")
    ParticipationRequestResponse toResponse(ParticipationRequest participationRequest);
}