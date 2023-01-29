package ru.practicum.ewm.controllers.publ.categories;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dtos.categories.CategoryList;
import ru.practicum.ewm.services.categories.CategoryService;
import ru.practicum.ewm.services.stats.StatsUpdateService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public: Categories")
public class CategoryController {
    private final CategoryService service;
    private final StatsUpdateService statsUpdateService;

    @GetMapping
    @Operation(summary = "Get categories")
    public ResponseEntity<CategoryList> findCategories(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Getting a list of categories");
        statsUpdateService.saveUriHitStats(request);
        return ResponseEntity.ok(service.findCategories(from, size));
    }
}
