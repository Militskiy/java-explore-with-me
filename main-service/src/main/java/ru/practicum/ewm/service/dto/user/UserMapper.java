package ru.practicum.ewm.service.dto.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.service.model.user.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);

    Initiator toDto1(User user);
}