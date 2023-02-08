package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.location.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}