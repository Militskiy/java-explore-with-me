package ru.practicum.ewm.service.service.category;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.service.dto.category.CategoryCreateRequest;
import ru.practicum.ewm.service.dto.category.CategoryList;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.model.category.Category;

@Service
public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest createRequest);

    CategoryResponse updateCategory(CategoryCreateRequest updateRequest, Long id);

    void deleteCategory(Long id);

    CategoryList findCategories(Integer from, Integer size);

    CategoryResponse findCategory(Long id);

    Category findCategoryEntity(Long id);
}
