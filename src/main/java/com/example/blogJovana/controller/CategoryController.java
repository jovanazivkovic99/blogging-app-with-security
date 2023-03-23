package com.example.blogJovana.controller;

import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;
import com.example.blogJovana.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<CategoryDetailsResponse> createCategory(@Valid @RequestBody CategoryDetailsRequest postRequest) {
        return ResponseEntity.ok(categoryService.createCategory(postRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> updateCategory(@Valid @RequestBody CategoryDetailsRequest postRequest,
                                                                  @PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> deleteCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> getCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDetailsResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
