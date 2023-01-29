package ru.practicum.ewm.dtos.categories;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.models.categories.Category;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
@Value
@Builder
@Jacksonized
public class CategoryResponse implements Serializable {
    Long id;
    String name;
}