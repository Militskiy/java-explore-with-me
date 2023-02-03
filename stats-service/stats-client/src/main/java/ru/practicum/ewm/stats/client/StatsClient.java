package ru.practicum.ewm.stats.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import ru.practicum.ewm.stats.dto.HitStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


public class StatsClient {
    private final WebClient client;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public StatsClient(WebClient client) {
        this.client = client;
    }

    public Mono<HitCreateResponse> post(HitCreateRequest hitCreateRequest) {
        return client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(hitCreateRequest), HitCreateRequest.class)
                .retrieve()
                .bodyToMono(HitCreateResponse.class);
    }

    public Flux<HitStats> getStats(
            LocalDateTime start, LocalDateTime end, Collection<String> uris, Boolean isUnique
    ) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start.format(dateFormat))
                        .queryParam("end", end.format(dateFormat))
                        .queryParam("uris", uris)
                        .queryParam("isUnique", isUnique)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(HitStats.class);
    }
}
