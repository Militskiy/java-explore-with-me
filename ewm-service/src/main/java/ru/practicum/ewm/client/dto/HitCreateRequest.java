package ru.practicum.ewm.client.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class HitCreateRequest {
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp = LocalDateTime.now();
}
