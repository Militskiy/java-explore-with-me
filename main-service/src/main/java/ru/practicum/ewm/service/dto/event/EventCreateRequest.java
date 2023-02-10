package ru.practicum.ewm.service.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.service.annotation.EventNotEarly;
import ru.practicum.ewm.service.dto.location.Coordinates;
import ru.practicum.ewm.service.validator.SecondOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ru.practicum.ewm.service.model.event.Event} entity
 */
@Value
@Builder
@Jacksonized
@Validated
public class EventCreateRequest implements Serializable {
    @NotBlank(groups = Default.class)
    @Size(min = 20, max = 2000, groups = Default.class)
    @Schema(example = "Event long annotation")
    String annotation;
    @Positive(groups = Default.class)
    @NotNull(groups = Default.class)
    @Schema(example = "1")
    Long category;
    @NotBlank(groups = Default.class)
    @Schema(example = "Event long description")
    @Size(min = 20, max = 7000)
    String description;
    @NotNull(groups = Default.class)
    @EventNotEarly(groups = SecondOrder.class)
    LocalDateTime eventDate;
    @NotNull(groups = Default.class)
    @Schema(example = "{\"lat\": 55.75, \"lon\": 37.62}")
    Coordinates location;
    @Schema(example = "false")
    Boolean paid;
    @Schema(example = "10")
    Integer participantLimit;
    @Schema(example = "false")
    Boolean requestModeration;
    @NotBlank(groups = Default.class)
    @Schema(example = "Event title")
    @Size(min = 3, max = 120)
    String title;
}