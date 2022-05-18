package com.ruben.rfaf.designation.infrastructure;

import com.ruben.rfaf.designation.application.DesignationService;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.designation.infrastructure.dto.InputDesignationDTO;
import com.ruben.rfaf.designation.infrastructure.dto.OutputDesignationDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/designation")
@AllArgsConstructor
public class DesignationController {

    private final DesignationService designationService;

    @PostMapping("create")
    public OutputDesignationDTO create(InputDesignationDTO inputDesignationDTO) {
        return new OutputDesignationDTO(designationService.create(new Designation(inputDesignationDTO)));
    }

    @GetMapping("findById/{id}")
    public OutputDesignationDTO findById(@PathVariable String id) throws Exception {
        return new OutputDesignationDTO(designationService.findById(id));
    }

    @GetMapping("findAll")
    public List<OutputDesignationDTO> findAll() {
        List<OutputDesignationDTO> outputDesignationDTOList = new ArrayList<>();
        for (Designation designation :designationService.findAll()) {
            outputDesignationDTOList.add(new OutputDesignationDTO(designation));
        }
        return outputDesignationDTOList;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable String id) throws Exception {
        designationService.remove(id);
    }
}
