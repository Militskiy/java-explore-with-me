package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class HitStats {
    String app;
    String uri;
    Integer hits;
}
