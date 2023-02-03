package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.stats.dto.StatsResponse;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public interface StatsService {
    Mono<HitCreateResponse> createHit(HitCreateRequest hitCreateRequest);

    Mono<StatsResponse> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique);
}
