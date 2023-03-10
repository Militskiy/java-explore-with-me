package ru.practicum.ewm.stats.controller;

import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.stats.dto.StatsResponse;
import ru.practicum.ewm.stats.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @Operation(summary = "post stats")
    public Mono<ResponseEntity<HitCreateResponse>> createHit(@RequestBody @Valid HitCreateRequest request) {
        log.info("saving hit: {}", request);
        return statsService.createHit(request)
                .map(res -> ResponseEntity.status(201).body(res));
    }

    @GetMapping("/stats")
    @Operation(summary = "get stats")
    public Mono<ResponseEntity<StatsResponse>> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(defaultValue = "") Collection<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
            ) {
        log.info("sending stats with parameters: start:{}, end:{}, uris:{}, isUnique{}", start, end, uris, unique);
        return statsService.getStats(start, end, uris, unique)
                .map(ResponseEntity::ok);
    }
}
