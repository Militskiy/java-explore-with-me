package ru.practicum.ewm.service.service.user;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.user.UserCreateRequest;
import ru.practicum.ewm.service.dto.user.UserList;
import ru.practicum.ewm.service.dto.user.UserResponse;
import ru.practicum.ewm.service.model.user.User;

import java.util.Collection;

@Service
public interface UserService {
    UserResponse createUser(UserCreateRequest createRequest);

    UserList findUsers(Collection<Long> ids, Integer from, Integer size);

    User findUserEntity(Long id);

    void deleteUser(Long id);
}
