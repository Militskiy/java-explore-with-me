package ru.practicum.ewm.service.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.service.location.LocationService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/locations")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin: Locations")
public class LocationAdminController {
    private final LocationService service;

    @PostMapping
    @Operation(summary = "Add location")
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationCreateRequest createRequest) {
        log.info("Adding new location={}", createRequest);
        return ResponseEntity.status(201).body(service.createLocation(createRequest));
    }
}
