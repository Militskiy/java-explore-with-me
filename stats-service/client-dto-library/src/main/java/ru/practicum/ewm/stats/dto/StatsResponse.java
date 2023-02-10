package ru.practicum.ewm.stats.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class StatsResponse {
    @JsonValue
    List<HitStats> hitStats;
}
