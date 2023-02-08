package ru.practicum.ewm.service.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.annotation.EventNotEarly;
import ru.practicum.ewm.service.dto.location.Coordinates;
import ru.practicum.ewm.service.model.event.UserStateActions;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.event.Event} entity
 */
@Value
@Builder
@Jacksonized
@Valid
public class EventUpdateRequest implements Serializable {
    @Pattern(regexp = "^[^ ].*[^ .]$", message = "not valid")
    @Size(min = 20, max = 2000)
    @Schema(example = "Event long annotation")
    String annotation;
    @Positive
    @Schema(example = "1")
    Long category;
    @Pattern(regexp = "^[^ ].*[^ .]$", message = "not valid")
    @Schema(example = "Event long description")
    @Size(min = 20, max = 7000)
    String description;
    @EventNotEarly
    LocalDateTime eventDate;
    @Schema(example = "{\"lat\": 55.75, \"lon\": 37.62}")
    Coordinates location;
    @Schema(example = "false")
    Boolean paid;
    @Schema(example = "10")
    Integer participantLimit;
    @Schema(example = "false")
    Boolean requestModeration;
    @Pattern(regexp = "^[^ ].*[^ .]$", message = "not valid")
    @Schema(example = "Event title")
    @Size(min = 3, max = 120)
    String title;
    @Schema(example = "SEND_TO_REVIEW")
    UserStateActions stateAction;
}