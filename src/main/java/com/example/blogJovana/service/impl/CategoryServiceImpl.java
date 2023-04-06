package com.example.blogJovana.service.impl;

import com.example.blogJovana.exceptions.CategoryAlreadyExistsException;
import com.example.blogJovana.exceptions.CategoryNotFoundException;
import com.example.blogJovana.exceptions.UserNotFoundException;
import com.example.blogJovana.mapper.CategoryMapper;
import com.example.blogJovana.model.Category;
import com.example.blogJovana.model.User;
import com.example.blogJovana.repository.CategoryRepository;
import com.example.blogJovana.repository.UserRepository;
import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;
import com.example.blogJovana.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;

    @Override
    public CategoryDetailsResponse createCategory(String jwtEmail, CategoryDetailsRequest request) {
        User user = userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );

        Optional<Category> existingCategory = categoryRepository.findByTitle(request.title());
        if (existingCategory.isPresent()) {
            throw new CategoryAlreadyExistsException(request.title());
        }
        Category category = categoryMapper.toCategory(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDetails(savedCategory);
    }

    @Override
    public CategoryDetailsResponse updateCategory(String jwtEmail, Long categoryId, CategoryDetailsRequest request) {
        userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        category.setTitle(request.title());
        category.setDescription(request.description());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDetails(updatedCategory);
    }

    @Override
    public CategoryDetailsResponse deleteCategory(String jwtEmail, Long categoryId) {
        userRepository.findByEmail(jwtEmail).orElseThrow(
                () -> new UserNotFoundException(jwtEmail)
        );
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        categoryRepository.delete(category);
        return categoryMapper.toCategoryDetails(category);
    }

    @Override
    public CategoryDetailsResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        return categoryMapper.toCategoryDetails(category);
    }

    @Override
    public List<CategoryDetailsResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toCategoryDetailsList(categories);
    }
}
