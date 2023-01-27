package ru.practicum.ewm.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface StatsRepository extends ReactiveCrudRepository<Hit, Long> {
    Flux<Hit> findHitsByTimestampAfterAndTimestampBeforeAndUriIn(
            LocalDateTime start, LocalDateTime end, Collection<String> uris
    );

    Flux<Hit> findHitsByTimestampAfterAndTimestampBefore(LocalDateTime start, LocalDateTime end);
}
