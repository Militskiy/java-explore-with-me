package ru.practicum.ewm.controllers.admin.categories;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dtos.categories.CategoryCreateRequest;
import ru.practicum.ewm.dtos.categories.CategoryResponse;
import ru.practicum.ewm.services.categories.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Categories")
public class CategoryAdminController {
    private final CategoryService service;

    @PostMapping
    @Operation(summary = "Add new category")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreateRequest createRequest) {
        log.info("Creating category {}", createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(createRequest));
    }
}
