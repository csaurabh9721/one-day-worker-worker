package com.worker_service.Controllers;


import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.SubCategoryDto;
import com.worker_service.service.SubCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryController {
    private final SubCategoryService service;

    public SubCategoryController(SubCategoryService service) {
        this.service = service;
    }

    @PostMapping("/addSubCategory")
    public ResponseEntity<ApiResponse<SubCategoryDto>> saveSubCategory(@Valid @RequestBody SubCategoryDto dto) {
        SubCategoryDto saveSubCategory = service.saveSubCategory(dto);
        ApiResponse<SubCategoryDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), saveSubCategory, "SubCategory added successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAllSubCategory")
    public ResponseEntity<ApiResponse<List<SubCategoryDto>>> getAllSubCategory() {
        List<SubCategoryDto> saveSubCategories = service.subCategories();
        ApiResponse<List<SubCategoryDto>> response = new ApiResponse<>(HttpStatus.OK.value(), saveSubCategories, "SubCategory fetched successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAllSubCategoryByCategoryId/{id}")
    public ResponseEntity<ApiResponse<List<SubCategoryDto>>> getAllSubCategoryByCategoryId(@PathVariable Long id) {
        List<SubCategoryDto> saveSubCategories = service.getAllSubCategoryByCategoryId(id);
        ApiResponse<List<SubCategoryDto>> response = new ApiResponse<>(HttpStatus.OK.value(), saveSubCategories, "SubCategory fetched successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
