package ru.practicum.ewm.service.dto.category;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.model.category.Category;

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