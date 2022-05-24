package com.ruben.rfaf.category.infrastructure;

import com.ruben.rfaf.category.application.CategoryService;
import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.dto.InputCategoryDTO;
import com.ruben.rfaf.category.infrastructure.dto.OutputCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("create")
    public OutputCategoryDTO addCategory(@RequestBody InputCategoryDTO inputCategoryDTO) throws Exception {
        System.out.println(inputCategoryDTO);
        return new OutputCategoryDTO(categoryService.addCategory(new Category(inputCategoryDTO)));
    }

    @GetMapping("findAll")
    public List<OutputCategoryDTO> findAll() {
        List<OutputCategoryDTO> outputCategoryDTOList = new ArrayList<>();
        for (Category category : categoryService.findAll()) {
            outputCategoryDTOList.add(new OutputCategoryDTO(category));
        }
        return outputCategoryDTOList;
    }

    @GetMapping("findByID/{id}")
    public OutputCategoryDTO findByID(@PathVariable String id) throws Exception {
        return new OutputCategoryDTO(categoryService.findByID(id));
    }

    @DeleteMapping("deleteByID/{id}")
    public String deleteByID(@PathVariable String id) throws Exception {
        categoryService.deleteByID(id);
        return "Categoria borrada correctamente.";
    }

    @PutMapping("update/{id}")
    public OutputCategoryDTO updateCategory(@PathVariable String id, @RequestBody InputCategoryDTO inputCategoryDTO) throws Exception {
        return new OutputCategoryDTO(categoryService.update(id, new Category(inputCategoryDTO)));
    }
}
