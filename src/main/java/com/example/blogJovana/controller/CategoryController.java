package com.example.blogJovana.controller;

import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;
import com.example.blogJovana.service.impl.CategoryServiceImpl;
import com.example.blogJovana.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final JwtServiceImpl jwtService;

    @PostMapping
    public CategoryDetailsResponse createCategory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody CategoryDetailsRequest postRequest) {
        String emailJWT = jwtService.extractEmail(jwt);
        return categoryService.createCategory(emailJWT, postRequest);
    }

    @PutMapping("/{id}")
    public CategoryDetailsResponse updateCategory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody CategoryDetailsRequest postRequest,
            @PathVariable("id") Long categoryId) {
        String emailJWT = jwtService.extractEmail(jwt);
        return categoryService.updateCategory(emailJWT, categoryId, postRequest);
    }

    @DeleteMapping("/{id}")
    public CategoryDetailsResponse deleteCategory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                                  @PathVariable("id") Long categoryId) {
        String emailJWT = jwtService.extractEmail(jwt);
        return categoryService.deleteCategory(emailJWT, categoryId);
    }

    @GetMapping("/{id}")
    public CategoryDetailsResponse getCategory(@PathVariable("id") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping
    public List<CategoryDetailsResponse> getCategories() {
        return categoryService.getCategories();
    }
}
