package com.example.blogJovana.mapper;

import com.example.blogJovana.model.Category;
import com.example.blogJovana.request.CategoryDetailsRequest;
import com.example.blogJovana.response.CategoryDetailsResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(imports = {LocalDateTime.class}, componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {
    CategoryDetailsResponse toCategoryDetails(Category category);

    List<CategoryDetailsResponse> toCategoryDetailsList(List<Category> post);

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Category toCategory(CategoryDetailsRequest request);
}
