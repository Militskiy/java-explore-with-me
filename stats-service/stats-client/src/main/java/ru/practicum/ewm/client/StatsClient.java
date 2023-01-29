package ru.practicum.ewm.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dtos.HitCreateRequest;
import ru.practicum.ewm.dtos.HitCreateResponse;


public class StatsClient {
    private final WebClient client;

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
}
