package ru.practicum.ewm.service.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.location.Location} entity
 */
@Value
@Builder
@Jacksonized
@Valid
public class LocationUpdateRequest implements Serializable {
    @Pattern(regexp = "^[^ ].*[^ .]$", message = "not valid")
    @Schema(example = "Moscow")
    String name;
    Coordinates location;
    @Min(1)
    @Schema(example = "100000")
    Long range;
}