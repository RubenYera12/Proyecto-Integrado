package com.ruben.rfaf.referee.infrastructure;

import com.ruben.rfaf.referee.application.RefereeService;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import com.ruben.rfaf.referee.infrastructure.dto.OutputRefereeDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/referee")
@AllArgsConstructor
public class RefereeController {
    private final RefereeService refereeService;

    // Request para insertar un Arbitro en la base de datos
    @PostMapping("/create")
    public OutputRefereeDTO addReferee(@RequestBody InputRefereeDTO inputRefereeDTO) throws Exception {
        Referee referee = new Referee(inputRefereeDTO);

        return new OutputRefereeDTO(refereeService.addUser(referee));
    }

    //Request para leer todos los Arbitro
    @GetMapping("/getAll")
    public List<OutputRefereeDTO> getReferees() {
        List<OutputRefereeDTO> outputRefereeDTOList = new ArrayList<>();
        for (Referee referee : refereeService.findAll()) {
            outputRefereeDTOList.add(new OutputRefereeDTO(referee));
        }
        return outputRefereeDTOList;
    }

    //Request para leer un Arbitro por su ID
    @GetMapping("/getByID/{id}")
    public OutputRefereeDTO getRefereeByID(@PathVariable String id) throws Exception {
        return new OutputRefereeDTO(refereeService.findById(id));
    }

    //Request para leer un Arbitro por su ID
    @GetMapping("/getByEmail/{email}")
    public OutputRefereeDTO getRefereeByEmail(@PathVariable String email) throws Exception {
        return new OutputRefereeDTO(refereeService.findByEmail(email));
    }

    //Request para actualizar un Arbitro
    @PostMapping("/update")
    public OutputRefereeDTO updateReferee(@RequestBody InputRefereeDTO inputRefereeDTO) throws Exception {
        return new OutputRefereeDTO(refereeService.updateUser(new Referee(inputRefereeDTO)));
    }

    //Request para borrar un Arbitro
    @DeleteMapping("/delete/{id}")
    public String deleteReferee(@PathVariable String id) throws Exception {

        return refereeService.deleteById(id);
    }
}
