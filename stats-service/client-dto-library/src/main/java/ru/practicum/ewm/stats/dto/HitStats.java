package ru.practicum.ewm.stats.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class HitStats {
    String app;
    String uri;
    Long hits;
}
