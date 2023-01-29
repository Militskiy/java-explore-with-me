package ru.practicum.ewm.dtos.categories;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.models.categories.Category;

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
    @NotBlank(message = "Name must not be blank")
    String name;
}