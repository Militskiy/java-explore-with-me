package ru.practicum.ewm.service.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.service.dto.category.CategoryCreateRequest;
import ru.practicum.ewm.service.dto.category.CategoryList;
import ru.practicum.ewm.service.dto.category.CategoryMapper;
import ru.practicum.ewm.service.dto.category.CategoryResponse;
import ru.practicum.ewm.service.exception.ConflictException;
import ru.practicum.ewm.service.exception.NotFoundException;
import ru.practicum.ewm.service.model.category.Category;
import ru.practicum.ewm.service.repository.CategoryRepository;
import ru.practicum.ewm.service.repository.EventRepository;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final EventRepository eventRepository;
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
        checkCategory(id);
        if (eventRepository.existsByCategory_Id(id)) {
            throw new ConflictException("The category is not empty");
        }
        repository.deleteById(id);
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

    @Override
    public Category findCategoryEntity(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Category with id=" + id + " was not found")
        );
    }

    private void checkCategory(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Category with id=" + id + " was not found");
        }
    }
}
