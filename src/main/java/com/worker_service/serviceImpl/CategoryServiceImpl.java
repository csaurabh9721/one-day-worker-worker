package com.worker_service.serviceImpl;

import com.worker_service.dto.CategoryDto;
import com.worker_service.entity.Category;
import com.worker_service.repository.CategoryRepository;
import com.worker_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto saveCategory(CategoryDto dto) {
        Category savedCategory = repository.save(toEntity(dto));
        return toDto(savedCategory);
    }


    @Override
    public List<CategoryDto> categories() {
        List<Category> cat = repository.findAll();
        return cat.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Category toEntity(CategoryDto dto) {
        return mapper.map(dto, Category.class);
    }

    private CategoryDto toDto(Category category) {

        return mapper.map(category, CategoryDto.class);
    }
}
