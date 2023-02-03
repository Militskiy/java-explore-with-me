package ru.practicum.ewm.stats.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.stats.dto.HitStats;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class CustomStatsRepositoryImpl implements CustomStatsRepository {
    private final ReactiveMongoTemplate template;

    @Override
    public Flux<HitStats> getStats(LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique) {
        var makeProjection = Aggregation.project("app", "uri", "hits");
        var filterDates = Aggregation.match(new Criteria("timestamp").gt(start).lt(end));
        var countHits = Aggregation.group("app", "uri").count().as("hits");
        var sortByHits = Aggregation.sort(Sort.Direction.DESC, "hits");
        var filterByUris = Aggregation.match(new Criteria("uri").in(uris));
        Aggregation parameters;

        if (isUnique) {
            var filterUnique = Aggregation.group("app", "uri", "ip");
            if (uris.isEmpty()) {
                parameters = Aggregation.newAggregation(
                        filterDates, filterUnique, countHits, sortByHits, makeProjection
                );
            } else {
                parameters = Aggregation.newAggregation(
                        filterDates, filterUnique, countHits, sortByHits, makeProjection, filterByUris
                );
            }
        } else {
            if (uris.isEmpty()) {
                parameters = Aggregation.newAggregation(
                        filterDates, countHits, sortByHits, makeProjection
                );
            } else {
                parameters = Aggregation.newAggregation(
                        filterDates, countHits, sortByHits, makeProjection, filterByUris
                );
            }
        }
        return template.aggregate(parameters, "stats", HitStats.class);
    }
}
