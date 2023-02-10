package ru.practicum.ewm.stats.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.stats.model.Hit;

@Repository
public interface StatsMongoRepository extends ReactiveMongoRepository<Hit, String> {

}
