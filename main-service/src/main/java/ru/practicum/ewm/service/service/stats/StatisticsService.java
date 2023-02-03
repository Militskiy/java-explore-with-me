package ru.practicum.ewm.service.service.stats;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.practicum.ewm.stats.dto.HitStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public interface StatisticsService {
    void saveUriHitStats(HttpServletRequest request);

    Flux<HitStats> getStats(LocalDateTime start, Collection<Long> eventIds);
}
