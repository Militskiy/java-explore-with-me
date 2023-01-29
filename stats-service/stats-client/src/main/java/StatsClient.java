import dtos.HitCreateRequest;
import dtos.HitCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StatsClient {

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
