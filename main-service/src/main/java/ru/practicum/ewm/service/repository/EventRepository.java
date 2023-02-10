package ru.practicum.ewm.service.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.event.Event;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    @EntityGraph(value = "event-entity-graph")
    List<Event> findEventsByInitiator_Id(Long userId, PageRequest pageRequest);

    @EntityGraph(value = "event-entity-graph")
    Optional<Event> findEventByIdAndInitiator_Id(Long eventId, Long userId);

    Optional<Event> findByIdAndInitiator_Id(Long eventId, Long userId);

    boolean existsByCategory_Id(Long categoryId);

    @EntityGraph(value = "event-entity-graph")
    @NonNull
    Page<Event> findAll(@Nullable Predicate predicate, @Nullable Pageable pageable);

    Set<Event> findEventsByIdIn(Collection<Long> eventIds);
}
