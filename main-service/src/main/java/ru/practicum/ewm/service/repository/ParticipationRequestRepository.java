package ru.practicum.ewm.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.request.ParticipationRequest;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findParticipationRequestsByRequester_Id(Long userId);

    List<ParticipationRequest> findParticipationRequestsByIdIn(Collection<Long> requestIds);
}