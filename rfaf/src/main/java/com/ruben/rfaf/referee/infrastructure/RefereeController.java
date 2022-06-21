package com.ruben.rfaf.referee.infrastructure;

import com.ruben.rfaf.category.infrastructure.repository.CategoryRepository;
import com.ruben.rfaf.referee.application.RefereeService;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import com.ruben.rfaf.referee.infrastructure.dto.OutputRefereeDTO;
import com.ruben.rfaf.shared.security.MyController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/referee")
@AllArgsConstructor
public class RefereeController {
    private final RefereeService refereeService;
    private final CategoryRepository categoryRepository;
    private MyController myController;

    // Request para insertar un Arbitro en la base de datos
    @PostMapping("/create")
    public ResponseEntity<OutputRefereeDTO> addReferee(@RequestBody InputRefereeDTO inputRefereeDTO) throws Exception {
        Referee referee = new Referee(inputRefereeDTO);
        if (inputRefereeDTO.getCategory_id()==null)
            throw new Exception("Selecciona una categoría");
        referee.setCategory(categoryRepository.findById(inputRefereeDTO.getCategory_id()).orElseThrow(()-> new Exception("No se ha encontrado la categoría indicada.")));

        return ResponseEntity.ok(new OutputRefereeDTO(refereeService.addUser(referee)));
    }

    //Request para leer todos los Arbitro
    @GetMapping("/getAll")
    public ResponseEntity<List<OutputRefereeDTO>> getReferees() {
        List<OutputRefereeDTO> outputRefereeDTOList = new ArrayList<>();
        for (Referee referee : refereeService.findAll()) {
            outputRefereeDTOList.add(new OutputRefereeDTO(referee));
        }
        return ResponseEntity.ok(outputRefereeDTOList);
    }

    //Request para leer un Arbitro por su ID
    @GetMapping("/getByID/{id}")
    public ResponseEntity<OutputRefereeDTO> getRefereeByID(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputRefereeDTO(refereeService.findById(id)));
    }

    //Request para leer un Arbitro por su ID
    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<OutputRefereeDTO> getRefereeByEmail(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(new OutputRefereeDTO(refereeService.findByEmail(email)));
    }

    @GetMapping("getByCategoryName/{name}")
    public ResponseEntity<List<OutputRefereeDTO>> getByCategoryName(@PathVariable String name) throws Exception {
        List<OutputRefereeDTO> outputRefereeDTOList = new ArrayList<>();
        for (Referee referee:refereeService.findByCategory(name) ) {
            outputRefereeDTOList.add(new OutputRefereeDTO(referee));
        }
        return ResponseEntity.ok(outputRefereeDTOList);
    }

    //Request para actualizar un Arbitro
    @PostMapping("/update/{id}")
    public ResponseEntity<OutputRefereeDTO> updateReferee(@RequestBody InputRefereeDTO inputRefereeDTO,@PathVariable String id) throws Exception {
        Referee referee = new Referee(inputRefereeDTO);
        if (inputRefereeDTO.getCategory_id()==null)
            throw new Exception("Selecciona una categoría");
        referee.setCategory(categoryRepository.findById(inputRefereeDTO.getCategory_id()).orElseThrow(()-> new Exception("No se ha encontrado la categoría indicada.")));

        return ResponseEntity.ok(new OutputRefereeDTO(refereeService.updateUser(referee,id)));
    }

    //Request para borrar un Arbitro
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReferee(@PathVariable String id) throws Exception {

        return ResponseEntity.ok(refereeService.deleteById(id));
    }
}
