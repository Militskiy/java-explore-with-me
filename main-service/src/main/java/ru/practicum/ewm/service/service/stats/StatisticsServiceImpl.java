package ru.practicum.ewm.service.service.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.dto.HitCreateRequest;
import ru.practicum.ewm.stats.dto.HitCreateResponse;
import ru.practicum.ewm.stats.dto.HitStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    private final StatsClient client;
    private static final String APP = "ewm-service";

    @Override
    public void saveUriHitStats(HttpServletRequest request) {
        uploadUriHit(makeHitCreateRequest(request.getRequestURI(), request.getRemoteAddr()))
                .subscribe(hitCreateResponse -> log.info("Successfully sent hit info: {}", hitCreateResponse));
    }

    @Override
    public Flux<HitStats> getStats(LocalDateTime start, Collection<Long> eventIds) {
        var end = LocalDateTime.now();
        var uris = eventIds.stream()
                .map(id -> "/events/" + id)
                .collect(Collectors.toList());
        return client.getStats(start, end, uris, true);
    }

    private HitCreateRequest makeHitCreateRequest(String uri, String ip) {
        return HitCreateRequest.builder()
                .app(APP)
                .uri(uri)
                .ip(ip)
                .build();
    }

    private Mono<HitCreateResponse> uploadUriHit(HitCreateRequest createRequest) {
        return client.post(createRequest);
    }
}
