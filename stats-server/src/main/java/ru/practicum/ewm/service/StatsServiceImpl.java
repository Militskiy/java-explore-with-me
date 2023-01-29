package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.HitCreateRequest;
import ru.practicum.ewm.dto.HitMapper;
import ru.practicum.ewm.dto.HitCreateResponse;
import ru.practicum.ewm.dto.StatsResponse;
import ru.practicum.ewm.repository.CustomStatsRepository;
import ru.practicum.ewm.repository.StatsMongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsMongoRepository mongoRepository;
    private final CustomStatsRepository statsRepository;
    private final HitMapper mapper;


    @Override
    public Mono<HitCreateResponse> createHit(HitCreateRequest hitCreateRequest) {
        return mongoRepository.save(mapper.toDocument(hitCreateRequest)).map(mapper::toResponseFromMongo);
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