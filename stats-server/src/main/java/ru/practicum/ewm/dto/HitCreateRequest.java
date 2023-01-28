package ru.practicum.ewm.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Valid
@Value
@Builder
@Jacksonized
public class HitCreateRequest {
    @NotBlank(message = "app field must not be blank")
    String app;
    @NotBlank(message = "uri field must not be blank")
    String uri;
    @NotBlank(message = "ip field must not be blank")
    String ip;
    LocalDateTime timestamp = LocalDateTime.now();
}
