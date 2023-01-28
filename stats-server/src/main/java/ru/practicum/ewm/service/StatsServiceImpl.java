package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import ru.practicum.ewm.dto.HitCreateRequest;
import ru.practicum.ewm.dto.HitCreateResponse;
import ru.practicum.ewm.dto.HitMapper;
import ru.practicum.ewm.dto.HitStats;
import ru.practicum.ewm.dto.StatsResponse;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            return getStatsMonoFromFlux(
                    statsRepository.findHitsByTimestampAfterAndTimestampBefore(start, end), isUnique
            );
        } else {
            return getStatsMonoFromFlux(
                    statsRepository.findHitsByTimestampAfterAndTimestampBeforeAndUriIn(start, end, uris), isUnique
            );
        }
    }

    private Mono<StatsResponse> getStatsMonoFromFlux(Flux<Hit> fluxOfHits, Boolean isUnique) {
        return fluxOfHits
                .collectList()
                .map(list -> isUnique ? getUniqueIpStats(list) : getNonUniqueIpStats(list));
    }

    private StatsResponse getUniqueIpStats(List<Hit> hitsList) {
        Map<Tuple2<String, String>, Set<String>> uniqueHits = new HashMap<>();

        hitsList.forEach(hit -> {
            var key = Tuples.of(hit.getApp(), hit.getUri());
            if (!uniqueHits.containsKey(key)) {
                uniqueHits.put(key, new HashSet<>());
                uniqueHits.get(key).add(hit.getIp());
            }
            uniqueHits.get(key).add(hit.getIp());
        });

        return StatsResponse.builder()
                .hitStats(
                        uniqueHits.entrySet()
                                .stream()
                                .map(entry -> HitStats.builder()
                                        .app(entry.getKey().getT1())
                                        .uri(entry.getKey().getT2())
                                        .hits(entry.getValue().size())
                                        .build())
                                .sorted(Comparator.comparingInt(HitStats::getHits).reversed())
                                .collect(Collectors.toList()))
                .build();
    }

    private StatsResponse getNonUniqueIpStats(List<Hit> hitsList) {
        Map<Tuple2<String, String>, Integer> hits = new HashMap<>();

        hitsList.forEach(hit -> {
            var key = Tuples.of(hit.getApp(), hit.getUri());
            if (!hits.containsKey(key)) {
                hits.put(key, 1);
            } else {
                hits.put(key, hits.get(key) + 1);
            }
        });

        return StatsResponse.builder()
                .hitStats(hits.entrySet()
                        .stream()
                        .map(entry -> HitStats.builder()
                                .app(entry.getKey().getT1())
                                .uri(entry.getKey().getT2())
                                .hits(entry.getValue())
                                .build())
                        .sorted(Comparator.comparingInt(HitStats::getHits).reversed())
                        .collect(Collectors.toList()))
                .build();
    }
}
