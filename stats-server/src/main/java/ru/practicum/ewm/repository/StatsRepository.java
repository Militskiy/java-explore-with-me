package ru.practicum.ewm.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.dto.HitStats;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface StatsRepository extends ReactiveCrudRepository<Hit, Long> {

    @Query("select app, uri, count(distinct ip) as hits " +
            "from hits " +
            "where created between :start and :end and uri in (:uris) " +
            "group by app, uri " +
            "order by hits desc")
    Flux<HitStats> findUniqueHitsBetweenDates(LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("select app, uri, count(ip) as hits " +
            "from hits " +
            "where created between :start and :end and uri in (:uris) " +
            "group by app, uri " +
            "order by hits desc")
    Flux<HitStats> findAllHitsBetweenDates(LocalDateTime start, LocalDateTime end, Collection<String> uris);

    @Query("select app, uri, count(ip) as hits " +
            "from hits " +
            "where created between :start and :end " +
            "group by app, uri " +
            "order by hits desc")
    Flux<HitStats> findAllHits(LocalDateTime start, LocalDateTime end);

    @Query("select app, uri, count(distinct ip) as hits " +
            "from hits " +
            "where created between :start and :end " +
            "group by app, uri " +
            "order by hits desc")
    Flux<HitStats> findAllUniqueHits(LocalDateTime start, LocalDateTime end);
}
