package ru.practicum.ewm.service.service.location;

import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationList;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.dto.location.LocationUpdateRequest;
import ru.practicum.ewm.service.model.location.Location;

public interface LocationService {
    LocationResponse createLocation(LocationCreateRequest createRequest);

    Location getLocationEntity(Long locationId);

    LocationResponse findLocation(Long locationId);

    LocationList findAllLocations(Integer from, Integer size);

    void deleteLocation(Long locationId);

    LocationResponse updateLocation(LocationUpdateRequest locationUpdateRequest, Long locationId);
}
