package ru.practicum.ewm.service.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.compilation.CompilationCreateRequest;
import ru.practicum.ewm.service.dto.compilation.CompilationResponse;
import ru.practicum.ewm.service.dto.compilation.CompilationUpdateRequest;
import ru.practicum.ewm.service.service.compilation.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Compilations")
@Validated
public class CompilationAdminController {
    private final CompilationService compilationService;

    @PostMapping
    @Operation(summary = "create compilation")
    public ResponseEntity<CompilationResponse> createCompilation(@RequestBody @Valid CompilationCreateRequest request) {
        log.info("Creating compilation: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.createCompilation(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete compilation")
    public ResponseEntity<Void> deleteCompilation(@PathVariable @Positive Long id) {
        log.info("Deleting compilation with id={}", id);
        compilationService.deleteCompilation(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update compilation")
    public ResponseEntity<CompilationResponse> updateCompilation(
            @RequestBody CompilationUpdateRequest request,
            @PathVariable @Positive Long id
    ) {
        log.info("Updating compilation with id={}", id);
        return ResponseEntity.ok(compilationService.updateCompilation(request, id));
    }
}
