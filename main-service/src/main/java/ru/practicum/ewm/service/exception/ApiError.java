package ru.practicum.ewm.service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@Jacksonized
public class ApiError {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<String> errors;
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp = LocalDateTime.now();
}
