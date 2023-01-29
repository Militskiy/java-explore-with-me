package ru.practicum.ewm.services.categories;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dtos.categories.CategoryCreateRequest;
import ru.practicum.ewm.dtos.categories.CategoryResponse;
import ru.practicum.ewm.dtos.categories.CategoryList;

@Service
public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest createRequest);

    CategoryResponse updateCategory(CategoryCreateRequest updateRequest, Long id);

    void deleteCategory(Long id);

    CategoryList findCategories(Integer from, Integer size);

    CategoryResponse findCategory(Long id);
}
