package ru.practicum.ewm.service.dto.compilation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CompilationList {
    @JsonValue
    List<CompilationResponse> compilations;
}
