package com.ruben.rfaf.category.application;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) throws Exception {
        Optional<Category> categoryCheck = categoryRepository.findByName(category.getName());
        if (categoryCheck.isPresent())
            throw new Exception("Ya existe la categoria: "+category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByID(String id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception("No existe una categoria con ID: " + id));
    }

    @Override
    public Category findByName(String name) throws Exception {
        return categoryRepository.findByName(name).orElseThrow(() -> new Exception("No existe la categoria: " + name));
    }

    @Override
    public void deleteByID(String id) throws Exception {
        Category category = findByID(id);
        categoryRepository.delete(category);
    }

    @Override
    public Category update(String id, Category category) throws Exception {
        Category categoryCheck = categoryRepository.findById(id).orElseThrow(() -> new Exception("No se ha encontrado la categoria con ID: " + id));
        if (category.getName() != null) categoryCheck.setName(category.getName());
        return categoryRepository.save(categoryCheck);
    }
}
