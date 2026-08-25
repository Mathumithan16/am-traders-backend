package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.CategoryRequestDto;
import com.amtraders.oms.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto getCategoryById(Long id);
}
