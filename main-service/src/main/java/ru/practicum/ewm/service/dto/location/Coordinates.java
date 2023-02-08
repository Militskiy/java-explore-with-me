package ru.practicum.ewm.service.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
@Builder
@Jacksonized
@Valid
public class Coordinates implements Serializable {
    @Schema(example = "55.751244")
    @NotNull
    Double lat;
    @NotNull
    @Schema(example = "37.618423")
    Double lon;
}