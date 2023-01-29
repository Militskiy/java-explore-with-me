package ru.practicum.ewm.util.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.util.client.dto.HitCreateRequest;
import ru.practicum.ewm.util.client.dto.HitCreateResponse;

@Service
@RequiredArgsConstructor
public class StatsWebClient {

    @Value("${stats-server.url}")
    private String server;
    private final WebClient client = WebClient.create(server);

    public Mono<HitCreateResponse> post(HitCreateRequest hitCreateRequest) {
        return client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(hitCreateRequest), HitCreateRequest.class)
                .retrieve()
                .bodyToMono(HitCreateResponse.class);
    }
}
