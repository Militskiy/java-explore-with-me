package ru.practicum.ewm.service.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Value
@Builder
@Jacksonized
public class LocationDto implements Serializable {
    @Schema(example = "55.75")
    Float lat;
    @Schema(example = "37.62")
    Float lon;
}