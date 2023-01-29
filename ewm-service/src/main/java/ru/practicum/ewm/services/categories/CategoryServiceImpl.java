package ru.practicum.ewm.services.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dtos.categories.CategoryCreateRequest;
import ru.practicum.ewm.dtos.categories.CategoryList;
import ru.practicum.ewm.dtos.categories.CategoryMapper;
import ru.practicum.ewm.dtos.categories.CategoryResponse;
import ru.practicum.ewm.exceptions.NotFoundException;
import ru.practicum.ewm.models.categories.Category;
import ru.practicum.ewm.repositories.categories.CategoryRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest createRequest) {
        return mapper.toResponse(repository.save(mapper.toEntity(createRequest)));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(CategoryCreateRequest updateRequest, Long id) {
        Category category = findCategoryEntity(id);
        return mapper.toResponse(repository.save(mapper.partialUpdate(updateRequest, category)));
    }

    @Override
    public void deleteCategory(Long id) {

    }

    @Override
    public CategoryList findCategories(Integer from, Integer size) {
        return CategoryList
                .builder()
                .categories(
                        repository.findAll(PageRequest.of(from, size)).get()
                                .map(mapper::toResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public CategoryResponse findCategory(Long id) {
        return mapper.toResponse(findCategoryEntity(id));
    }

    private Category findCategoryEntity(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Category with id " + id + " not found")
        );
    }
}
