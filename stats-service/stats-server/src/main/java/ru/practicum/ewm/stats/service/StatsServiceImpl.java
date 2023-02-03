package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.stats.mapper.HitMapper;
import ru.practicum.ewm.stats.dto.StatsResponse;
import ru.practicum.ewm.stats.model.Hit;
import ru.practicum.ewm.stats.repository.CustomStatsRepository;
import ru.practicum.ewm.stats.repository.StatsMongoRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsMongoRepository mongoRepository;
    private final CustomStatsRepository statsRepository;
    private final HitMapper mapper;


    @Override
    public Mono<HitCreateResponse> createHit(HitCreateRequest hitCreateRequest) {
        Hit hit = mapper.toDocument(hitCreateRequest);
        hit.setTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return mongoRepository.save(hit).map(mapper::toResponse);
    }

    @Override
    public Mono<StatsResponse> getStats(
            LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique
    ) {
        return statsRepository.getStats(start, end, uris, isUnique)
                .collectList()
                .map(list -> StatsResponse.builder().hitStats(list).build());
    }
}