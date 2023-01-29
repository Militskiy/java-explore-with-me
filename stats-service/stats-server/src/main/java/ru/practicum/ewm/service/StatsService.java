package ru.practicum.ewm.service;

import dtos.HitCreateRequest;
import dtos.HitCreateResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.StatsResponse;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public interface StatsService {
    Mono<HitCreateResponse> createHit(HitCreateRequest hitCreateRequest);

    Mono<StatsResponse> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique);
}
