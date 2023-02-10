package ru.practicum.ewm.stats.repository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.stats.dto.HitStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface CustomStatsRepository {
    Flux<HitStats> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique);
}
