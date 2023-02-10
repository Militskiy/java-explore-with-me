package ru.practicum.ewm.service.dto.location;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class LocationList {
    @JsonValue
    List<LocationResponse> locations;
}
