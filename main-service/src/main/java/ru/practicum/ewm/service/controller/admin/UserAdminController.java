package ru.practicum.ewm.service.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.user.UserCreateRequest;
import ru.practicum.ewm.service.dto.user.UserList;
import ru.practicum.ewm.service.dto.user.UserResponse;
import ru.practicum.ewm.service.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Users")
@Validated
public class UserAdminController {
    private final UserService service;

    @PostMapping
    @Operation(summary = "Add new user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest createRequest) {
        log.info("Creating user: {}", createRequest);
        return ResponseEntity.status(201).body(service.createUser(createRequest));
    }

    @GetMapping
    @Operation(summary = "Getting users by parameters")
    public ResponseEntity<UserList> findUsersBy(
            @RequestParam(required = false) Collection<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info(
                "Getting a list of users with parameters: ids(all if empty): {}, from page: {}, page size: {}",
                ids, from, size
        );
        return ResponseEntity.ok(service.findUsers(ids, from, size));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<Void> deleteUser(@PathVariable @Min(1) Long userId) {
        log.info("Deleting user with ID: {}", userId);
        service.deleteUser(userId);
        return ResponseEntity.status(204).build();
    }
}
