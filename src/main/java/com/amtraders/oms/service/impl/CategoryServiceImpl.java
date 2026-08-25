package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.CategoryRequestDto;
import com.amtraders.oms.dto.response.CategoryResponseDto;
import com.amtraders.oms.entity.Category;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.CategoryRepository;
import com.amtraders.oms.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        Category saved = categoryRepository.save(category);
        return mapToResponseDto(saved);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponseDto(category);
    }

    private CategoryResponseDto mapToResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName()
        );
    }
}
