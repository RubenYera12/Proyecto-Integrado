package com.ruben.rfaf.category.application;

import com.ruben.rfaf.category.domain.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory(Category category);

    List<Category> findAll();

    Category findByID(String id) throws Exception;

    void deleteByID(String id) throws Exception;

    Category update(String id, Category category) throws Exception;
}
