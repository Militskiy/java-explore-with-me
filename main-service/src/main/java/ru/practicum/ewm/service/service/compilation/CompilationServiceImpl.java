package ru.practicum.ewm.service.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.compilation.CompilationCreateRequest;
import ru.practicum.ewm.service.dto.compilation.CompilationList;
import ru.practicum.ewm.service.dto.compilation.CompilationMapper;
import ru.practicum.ewm.service.dto.compilation.CompilationResponse;
import ru.practicum.ewm.service.dto.compilation.CompilationUpdateRequest;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.compilation.Compilation;
import ru.practicum.ewm.service.repository.CompilationRepository;
import ru.practicum.ewm.service.service.event.EventService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventService eventService;
    private final CompilationMapper mapper;

    @Override
    @Transactional
    public CompilationResponse createCompilation(CompilationCreateRequest createRequest) {
        var events = eventService.findEventsFrom(createRequest.getEvents());
        return mapper.toResponse(repository.save(mapper.toEntity(createRequest, events)));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compilationId) {
        if (repository.existsById(compilationId)) {
            repository.deleteById(compilationId);
        } else {
            throw new NotFoundException("Compilation with id=" + compilationId + " was not found");
        }
    }

    @Override
    @Transactional
    public CompilationResponse updateCompilation(CompilationUpdateRequest updateRequest, Long compilationId) {
        var compilation = findCompilationEntity(compilationId);
        mapper.update(updateRequest, compilation);
        if (updateRequest.getEvents() != null) {
            var events = eventService.findEventsFrom(updateRequest.getEvents());
            compilation.setEvents(events);
        }
        return mapper.toResponse(repository.save(compilation));
    }

    @Override
    public CompilationList findCompilations(Boolean pinned, Integer from, Integer size) {
        List<CompilationResponse> result;
        if (pinned != null) {
            result = repository.findByPinned(pinned, PageRequest.of(from, size))
                    .stream().map(mapper::toResponse).collect(Collectors.toList());
        } else {
            result = repository.findAll(PageRequest.of(from, size))
                    .stream().map(mapper::toResponse).collect(Collectors.toList());
        }
        return CompilationList.builder().compilations(result).build();
    }

    @Override
    public CompilationResponse findCompilation(Long compilationId) {
        return mapper.toResponse(findCompilationEntity(compilationId));
    }

    private Compilation findCompilationEntity(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Compilation with id=" + id + " was not found")
        );
    }
}
