package ru.practicum.ewm.service.dto.user;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class UserList {
    @JsonValue
    List<UserResponse> users;
}
