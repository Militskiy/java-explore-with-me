package ru.practicum.ewm.service.service.user;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.user.UserCreateRequest;
import ru.practicum.ewm.service.dto.user.UserList;
import ru.practicum.ewm.service.dto.user.UserMapper;
import ru.practicum.ewm.service.dto.user.UserResponse;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.user.QUser;
import ru.practicum.ewm.service.model.user.User;
import ru.practicum.ewm.service.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserResponse createUser(UserCreateRequest createRequest) {
        return mapper.toResponse(repository.save(mapper.toEntity(createRequest)));
    }

    @Override
    public UserList findUsers(Collection<Long> ids, Integer from, Integer size) {
        BooleanBuilder builder = new BooleanBuilder();
        if (ids != null && !ids.isEmpty()) {
            builder.and(QUser.user.id.in(ids));
        }
        return UserList.builder()
                .users(repository.findAll(builder, PageRequest.of(from, size)).toList()
                        .stream().map(mapper::toResponse).collect(Collectors.toList()))
                .build();
    }

    @Override
    public User findUserEntity(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id=" + id + " not found")
        );
    }

    @Override
    public void deleteUser(Long id) {
        checkUser(id);
        repository.deleteById(id);
    }

    @Override
    public void checkUser(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("User with id=" + id + " not found");
        }
    }
}
