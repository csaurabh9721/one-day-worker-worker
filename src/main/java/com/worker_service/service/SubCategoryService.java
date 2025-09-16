package com.worker_service.service;
import com.worker_service.dto.SubCategoryDto;
import java.util.List;

public interface SubCategoryService {
    SubCategoryDto saveSubCategory(SubCategoryDto dto);
    List<SubCategoryDto> subCategories();
    List<SubCategoryDto> getAllSubCategoryByCategoryId(Long id);
}
