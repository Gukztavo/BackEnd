package com.example.projeto_api.controllers;

import com.example.projeto_api.domain.category.Category;
import com.example.projeto_api.dto.CategoryDTO;
import com.example.projeto_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

    @Autowired
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    // Criar nova categoria
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO body) {
        if (body.getNome() == null || body.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Category name cannot be null or empty");
        }

        Category newCategory = new Category();
        newCategory.setNome(body.getNome());
        repository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    // Listar todas as categorias
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = repository.findAll();
        return ResponseEntity.ok(categories);
    }

    // Atualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO body) {
        Optional<Category> categoryOptional = repository.findById(id);
        if (categoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        Category category = categoryOptional.get();
        if (body.getNome() != null && !body.getNome().trim().isEmpty()) {
            category.setNome(body.getNome());
            repository.save(category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.badRequest().body("Category name cannot be null or empty");
        }
    }

    // Deletar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
