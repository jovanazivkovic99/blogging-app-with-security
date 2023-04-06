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
    public ResponseEntity<CategoryDetailsResponse> createCategory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody CategoryDetailsRequest postRequest) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(categoryService.createCategory(emailJWT, postRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> updateCategory(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
            @Valid @RequestBody CategoryDetailsRequest postRequest,
            @PathVariable("id") Long categoryId) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(categoryService.updateCategory(emailJWT, categoryId, postRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> deleteCategory(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                                                  @PathVariable("id") Long categoryId) {
        String emailJWT = jwtService.extractEmail(jwt);
        return ResponseEntity.ok(categoryService.deleteCategory(emailJWT, categoryId));
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
