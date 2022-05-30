package com.ruben.rfaf.category.infrastructure.dto;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputCategoryDTO {
    private String name;

    public OutputCategoryDTO(Category category){
        setName(category.getName());
    }
}
