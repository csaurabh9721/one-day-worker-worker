package com.worker_service.serviceImpl;

import com.worker_service.dto.SubCategoryDto;
import com.worker_service.entity.SubCategory;
import com.worker_service.repository.SubCategoryRepository;
import com.worker_service.service.SubCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository repository;
    private final ModelMapper mapper;

    public SubCategoryServiceImpl(SubCategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SubCategoryDto saveSubCategory(SubCategoryDto dto) {
        SubCategory savedCategory = repository.save(toEntity(dto));
        return toDto(savedCategory);
    }

    @Override
    public List<SubCategoryDto> subCategories() {
        List<SubCategory> cat = repository.findAll();
        return cat.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SubCategoryDto> getAllSubCategoryByCategoryId(Long id) {
        List<SubCategory> cat = repository.findByCategoryId(id);
        return cat.stream().map(this::toDto).toList();
    }

    private SubCategory toEntity(SubCategoryDto dto) {
        return mapper.map(dto, SubCategory.class);
    }

    private SubCategoryDto toDto(SubCategory subCategory) {
        return mapper.map(subCategory, SubCategoryDto.class);
    }

}
