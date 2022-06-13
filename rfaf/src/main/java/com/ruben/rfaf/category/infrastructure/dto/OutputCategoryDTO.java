package com.ruben.rfaf.category.infrastructure.dto;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputCategoryDTO {
    private String id;
    private String name;
    private List<Referee> refereeList;

    public OutputCategoryDTO(Category category){
        setId(category.getId());
        setName(category.getName());
        setRefereeList(category.getRefereeList());
    }
}
