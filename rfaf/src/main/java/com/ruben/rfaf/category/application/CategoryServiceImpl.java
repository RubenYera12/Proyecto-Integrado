package com.ruben.rfaf.category.application;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
