package ru.practicum.ewm.services.stats;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface StatsUpdateService {
    void saveUriHitStats(HttpServletRequest request);
}
