package ru.practicum.ewm.dtos.categories;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CategoryList {
    @JsonValue
    List<CategoryResponse> categories;
}
