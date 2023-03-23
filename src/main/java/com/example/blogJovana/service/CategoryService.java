package com.example.blogJovana.service;

import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailsResponse createCategory(CategoryDetailsRequest request);

    CategoryDetailsResponse updateCategory(Long categoryId, CategoryDetailsRequest request);

    CategoryDetailsResponse deleteCategory(Long categoryId);

    CategoryDetailsResponse getCategory(Long categoryId);

    List<CategoryDetailsResponse> getCategories();
}
