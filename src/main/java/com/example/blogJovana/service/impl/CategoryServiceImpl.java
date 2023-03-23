package com.example.blogJovana.service.impl;

import com.example.blogJovana.exceptions.CategoryNotFound;
import com.example.blogJovana.mapper.CategoryMapper;
import com.example.blogJovana.model.Category;
import com.example.blogJovana.repository.CategoryRepository;
import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;
import com.example.blogJovana.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDetailsResponse createCategory(CategoryDetailsRequest request) {
        Category category = categoryMapper.toCategory(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDetails(savedCategory);
    }

    @Override
    public CategoryDetailsResponse updateCategory(Long categoryId, CategoryDetailsRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFound(HttpStatus.NOT_FOUND, "category not found", categoryId));

        category.setTitle(request.title());
        category.setDescription(request.description());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDetails(updatedCategory);
    }

    @Override
    public CategoryDetailsResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFound(HttpStatus.NOT_FOUND, "category not found", categoryId));
        categoryRepository.delete(category);
        return categoryMapper.toCategoryDetails(category);
    }

    @Override
    public CategoryDetailsResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFound(HttpStatus.NOT_FOUND, "category not found", categoryId));

        return categoryMapper.toCategoryDetails(category);
    }

    @Override
    public List<CategoryDetailsResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDetailsList(categories);
    }
}
