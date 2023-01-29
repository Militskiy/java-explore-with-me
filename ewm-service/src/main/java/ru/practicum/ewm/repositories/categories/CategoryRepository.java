package ru.practicum.ewm.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.models.categories.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}