package ru.practicum.ewm.service;

import dtos.HitCreateRequest;
import dtos.HitCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.HitMapper;
import ru.practicum.ewm.dto.StatsResponse;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.repository.CustomStatsRepository;
import ru.practicum.ewm.repository.StatsMongoRepository;

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
        return mongoRepository.save(hit).map(mapper::toResponseFromMongo);
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