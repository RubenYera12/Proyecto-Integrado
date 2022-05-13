package com.ruben.rfaf.category.infrastructure.repository;

import com.ruben.rfaf.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
