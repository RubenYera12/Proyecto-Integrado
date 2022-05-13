package com.ruben.rfaf.category.application;

import com.ruben.rfaf.category.domain.Category;

import java.util.List;

public interface CategoryService {
    public Category addCategory(Category category);

    List<Category> findAll();
}
