package ru.practicum.ewm.service.service.location;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.location.LocationCreateRequest;
import ru.practicum.ewm.service.dto.location.LocationList;
import ru.practicum.ewm.service.dto.location.LocationMapper;
import ru.practicum.ewm.service.dto.location.LocationResponse;
import ru.practicum.ewm.service.dto.location.LocationUpdateRequest;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.location.Location;
import ru.practicum.ewm.service.repository.LocationRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper mapper;
    private final GeometryFactory factory;

    @Override
    @Transactional
    public LocationResponse createLocation(LocationCreateRequest createRequest) {
        return mapper.toResponse(locationRepository.save(mapper.toEntity(createRequest, factory)));
    }

    @Override
    public Location getLocationEntity(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new NotFoundException("Location with id=" + locationId + " not found")
        );
    }

    @Override
    public LocationResponse findLocation(Long locationId) {
        return mapper.toResponse(getLocationEntity(locationId));
    }

    @Override
    public LocationList findAllLocations(Integer from, Integer size) {
        return LocationList.builder()
                .locations(
                        locationRepository.findAll(PageRequest.of(from, size)).stream().map(mapper::toResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new NotFoundException("Location with id=" + locationId + " not found");
        }
        locationRepository.deleteById(locationId);
    }

    @Override
    @Transactional
    public LocationResponse updateLocation(LocationUpdateRequest locationUpdateRequest, Long locationId) {
        var location = getLocationEntity(locationId);
        mapper.partialUpdate(locationUpdateRequest, location, factory);
        return mapper.toResponse(locationRepository.save(location));
    }
}
