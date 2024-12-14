package com.example.projeto_api.infra.security;


import com.example.projeto_api.domain.category.Category;
import com.example.projeto_api.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public Category createCategory(Category category) {
        if (category.getNome() == null || category.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setNome(updatedCategory.getNome());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}