package com.worker_service.service;

import com.worker_service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto saveCategory(CategoryDto dto);
    List<CategoryDto> categories();
}
