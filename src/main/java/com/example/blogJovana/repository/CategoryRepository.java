package com.example.blogJovana.repository;

import com.example.blogJovana.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
