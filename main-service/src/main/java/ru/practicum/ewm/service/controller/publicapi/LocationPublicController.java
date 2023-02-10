package ru.practicum.ewm.service.controller.publicapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.location.LocationList;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.service.location.LocationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping(path = "/locations")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public: Locations")
@Validated
public class LocationPublicController {
    private final LocationService service;

    @GetMapping
    @Operation(summary = "Get a list of saved locations")
    public ResponseEntity<LocationList> findAll(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Getting a list of all saved locations, from page={} size={}", from, size);
        return ResponseEntity.ok(service.findAllLocations(from, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get location by id")
    public ResponseEntity<LocationResponse> findLocation(@PathVariable @Positive Long id) {
        log.info("Getting location with id={}", id);
        return ResponseEntity.ok(service.findLocation(id));
    }
}
