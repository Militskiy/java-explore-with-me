package ru.practicum.ewm.service.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.dto.location.LocationUpdateRequest;
import ru.practicum.ewm.service.service.location.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/locations")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Locations")
@Validated
public class LocationAdminController {
    private final LocationService service;

    @PostMapping
    @Operation(summary = "Add location")
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationCreateRequest createRequest) {
        log.info("Adding new location={}", createRequest);
        return ResponseEntity.status(201).body(service.createLocation(createRequest));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update location")
    public ResponseEntity<LocationResponse> updateLocation(
            @RequestBody @Valid LocationUpdateRequest updateRequest,
            @PathVariable @Positive Long id
    ) {
        log.info("Updating location with id={}, update:{}", id, updateRequest);
        return ResponseEntity.ok(service.updateLocation(updateRequest, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete location")
    public ResponseEntity<Void> deleteLocation(@PathVariable @Positive Long id) {
        log.info("Deleting location with id={}", id);
        service.deleteLocation(id);
        return ResponseEntity.status(204).build();
    }
}
