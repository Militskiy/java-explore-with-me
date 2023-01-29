package ru.practicum.ewm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.practicum.ewm.model.Hit;

public interface StatsMongoRepository extends ReactiveMongoRepository<Hit, String> {

}
