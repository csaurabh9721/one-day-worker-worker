package com.worker_service.Controllers;

import com.worker_service.dto.ApiResponse;
import com.worker_service.dto.CategoryDto;
import com.worker_service.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/addCategory")
    public ResponseEntity<ApiResponse<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto dto) {
        CategoryDto savedCategory = service.saveCategory(dto);
        ApiResponse<CategoryDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), savedCategory, "Category added successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategory() {
        List<CategoryDto> categories = service.categories();
        ApiResponse<List<CategoryDto>> response = new ApiResponse<>(HttpStatus.OK.value(), categories, "Category fetched successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
