package ru.practicum.ewm.service.service.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.compilation.CompilationCreateRequest;
import ru.practicum.ewm.service.dto.compilation.CompilationList;
import ru.practicum.ewm.service.dto.compilation.CompilationResponse;
import ru.practicum.ewm.service.dto.compilation.CompilationUpdateRequest;

@Service
public interface CompilationService {
    CompilationResponse createCompilation(CompilationCreateRequest createRequest);

    void deleteCompilation(Long compilationId);

    CompilationResponse updateCompilation(CompilationUpdateRequest updateRequest, Long compilationId);

    CompilationList findCompilations(Boolean pinned, Integer from, Integer size);

    CompilationResponse findCompilation(Long compilationId);
}
