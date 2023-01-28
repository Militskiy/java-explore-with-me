package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class HitCreateResponse {
    Long id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}
