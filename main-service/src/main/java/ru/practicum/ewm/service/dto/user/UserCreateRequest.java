package ru.practicum.ewm.service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewm.service.model.user.User;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Value
@Builder
@Jacksonized
@Valid
public class UserCreateRequest implements Serializable {
    @Email
    @NotBlank
    @Schema(example = "user@mail.com")
    String email;
    @NotBlank
    @Schema(example = "User")
    String name;
}