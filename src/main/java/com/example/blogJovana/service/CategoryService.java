package com.example.blogJovana.service;

import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailsResponse createCategory(String jwtEmail, CategoryDetailsRequest request);

    CategoryDetailsResponse updateCategory(String jwtEmail, Long categoryId, CategoryDetailsRequest request);

    CategoryDetailsResponse deleteCategory(String jwtEmail, Long categoryId);

    CategoryDetailsResponse getCategory(Long categoryId);

    List<CategoryDetailsResponse> getCategories();
}
