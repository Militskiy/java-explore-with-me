package ru.practicum.ewm.services.stats;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dtos.HitCreateRequest;
import ru.practicum.ewm.dtos.HitCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsUpdateServiceImpl implements StatsUpdateService {
    private final StatsClient client;
    private static final String APP = "ewm-service";


    @Override
    public void saveUriHitStats(HttpServletRequest request) {
        uploadUriHit(makeHitCreateRequest(request.getRequestURI(), request.getRemoteAddr()))
                .subscribe(hitCreateResponse -> log.info("Successfully sent hit info: {}", hitCreateResponse));
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
