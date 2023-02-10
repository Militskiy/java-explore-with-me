package ru.practicum.ewm.service.dto.compilation;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.service.dto.event.EventMapper;
import ru.practicum.ewm.service.model.compilation.Compilation;
import ru.practicum.ewm.service.model.event.Event;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {EventMapper.class})
public interface CompilationMapper {

    @Mapping(target = "events", source = "events")
    Compilation toEntity(CompilationCreateRequest request, Set<Event> events);

    CompilationResponse toResponse(Compilation compilation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", ignore = true)
    Compilation update(
            CompilationUpdateRequest request, @MappingTarget Compilation compilation
    );
}