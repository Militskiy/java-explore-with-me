package ru.practicum.ewm.service.service.location;

import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.model.location.Location;

public interface LocationService {
    LocationResponse createLocation(LocationCreateRequest createRequest);

    Location getLocationEntity(Long locationId);
}
