package ru.practicum.ewm.service.service.location;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationMapper;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.location.Location;
import ru.practicum.ewm.service.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper mapper;
    private final GeometryFactory factory;

    @Override
    public LocationResponse createLocation(LocationCreateRequest createRequest) {
        return mapper.toResponse(locationRepository.save(mapper.toEntity(createRequest, factory)));
    }

    @Override
    public Location getLocationEntity(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NotFoundException("Location with id=" + locationId + " not found")
        );
    }
}
