package ru.practicum.ewm.service.controller.publicapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.compilation.CompilationList;
import ru.practicum.ewm.service.dto.compilation.CompilationResponse;
import ru.practicum.ewm.service.service.compilation.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public: Compilations")
@Validated
public class CompilationPublicController {
    private final CompilationService service;

    @GetMapping
    @Operation(summary = "Find compilation")
    public ResponseEntity<CompilationList> findCompilations(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Finding pinned={} compilations", pinned);
        return ResponseEntity.ok(service.findCompilations(pinned, from, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find compilation")
    public ResponseEntity<CompilationResponse> findCompilation(@PathVariable @Positive Long id) {
        log.info("Finding compilation with id={}", id);
        return ResponseEntity.ok(service.findCompilation(id));
    }
}
