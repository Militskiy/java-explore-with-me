package ru.practicum.ewm.service.controller.publicapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.category.CategoryList;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public: Categories")
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    @Operation(summary = "Get categories")
    public ResponseEntity<CategoryList> findCategories(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Getting a list of categories");
        return ResponseEntity.ok(service.findCategories(from, size));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> findCategory(
            @PathVariable @Positive Long categoryId
    ) {
        log.info("Finding category with id={}", categoryId);
        return ResponseEntity.ok(service.findCategory(categoryId));
    }
}
