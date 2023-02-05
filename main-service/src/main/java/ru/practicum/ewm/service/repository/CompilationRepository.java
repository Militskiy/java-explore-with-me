package ru.practicum.ewm.service.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.service.model.compilation.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long>,
        QuerydslPredicateExecutor<Compilation> {
    boolean existsById(Long compilationId);

    @EntityGraph(value = "compilation-entity-graph")
    @NonNull
    Page<Compilation> findAll(@Nullable Predicate predicate, @Nullable Pageable pageable);
}