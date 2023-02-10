package ru.practicum.ewm.service.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.model.category.Category;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
@Valid
@Value
@Builder
@Jacksonized
public class CategoryCreateRequest implements Serializable {
    @NotBlank
    @Schema(example = "Movies")
    String name;
}