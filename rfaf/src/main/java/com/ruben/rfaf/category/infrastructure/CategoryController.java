package com.ruben.rfaf.category.infrastructure;

import com.ruben.rfaf.category.application.CategoryService;
import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.dto.InputCategoryDTO;
import com.ruben.rfaf.category.infrastructure.dto.OutputCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping("create")
    public ResponseEntity<OutputCategoryDTO> addCategory(@RequestBody InputCategoryDTO inputCategoryDTO) throws Exception {
        System.out.println(inputCategoryDTO);
        return ResponseEntity.ok(new OutputCategoryDTO(categoryService.addCategory(new Category(inputCategoryDTO))));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<OutputCategoryDTO>> findAll() {
        List<OutputCategoryDTO> outputCategoryDTOList = new ArrayList<>();
        for (Category category : categoryService.findAll()) {
            outputCategoryDTOList.add(new OutputCategoryDTO(category));
        }
        return ResponseEntity.ok(outputCategoryDTOList);
    }

    @GetMapping("findByID/{id}")
    public ResponseEntity<OutputCategoryDTO> findByID(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputCategoryDTO(categoryService.findByID(id)));
    }

    @PostMapping("deleteByID/{id}")
    public ResponseEntity<String> deleteByID(@PathVariable String id) throws Exception {
        categoryService.deleteByID(id);
        return ResponseEntity.ok("Categoria borrada correctamente.");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OutputCategoryDTO> updateCategory(@PathVariable String id, @RequestBody InputCategoryDTO inputCategoryDTO) throws Exception {
        return ResponseEntity.ok(new OutputCategoryDTO(categoryService.update(id, new Category(inputCategoryDTO))));
    }
}
