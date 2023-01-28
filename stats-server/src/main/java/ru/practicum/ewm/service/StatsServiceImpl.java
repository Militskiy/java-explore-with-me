package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.HitCreateRequest;
import ru.practicum.ewm.dto.HitCreateResponse;
import ru.practicum.ewm.dto.HitMapper;
import ru.practicum.ewm.dto.StatsResponse;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final HitMapper mapper;

    @Transactional
    @Override
    public Mono<HitCreateResponse> createHit(HitCreateRequest hitCreateRequest) {
        return statsRepository.save(mapper.toEntity(hitCreateRequest)).map(mapper::toCreateResponse);
    }

    @Override
    public Mono<StatsResponse> getStats(
            LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique
    ) {
        if (uris.isEmpty()) {
            if (isUnique) {
                return statsRepository.findAllUniqueHits(start, end)
                        .collectList()
                        .map(list -> StatsResponse.builder().hitStats(list).build());
            } else {
                return statsRepository.findAllHits(start, end)
                        .collectList()
                        .map(list -> StatsResponse.builder().hitStats(list).build());
            }
        } else {
            if (isUnique) {
                return statsRepository.findUniqueHitsBetweenDates(start, end, uris)
                        .collectList()
                        .map(list -> StatsResponse.builder().hitStats(list).build());
            } else {
                return statsRepository.findAllHitsBetweenDates(start, end, uris)
                        .collectList()
                        .map(list -> StatsResponse.builder().hitStats(list).build());
            }
        }
    }
}