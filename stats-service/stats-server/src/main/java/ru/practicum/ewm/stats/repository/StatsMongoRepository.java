package ru.practicum.ewm.stats.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.practicum.ewm.stats.model.Hit;

public interface StatsMongoRepository extends ReactiveMongoRepository<Hit, String> {

}
