package com.example.projeto_api.repositories;

import com.example.projeto_api.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}