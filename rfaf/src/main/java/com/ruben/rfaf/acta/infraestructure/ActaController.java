package com.ruben.rfaf.acta.infraestructure;

import com.ruben.rfaf.acta.application.ActaService;
import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.acta.infraestructure.dto.ActaInputDto;
import com.ruben.rfaf.acta.infraestructure.dto.ActaOutputDto;
import com.ruben.rfaf.acta.infraestructure.dto.FullActaOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/acta")

public class ActaController {

    @Autowired
    private ActaService actaService;

    @PostMapping("/{id}")
    public ResponseEntity<FullActaOutputDto> save(@RequestBody ActaInputDto actaInputDto,@PathVariable String id) throws Exception {
        Acta acta = new Acta(actaInputDto);
            return ResponseEntity.ok(new FullActaOutputDto(actaService.update(acta, id)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ActaOutputDto> findActaById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new ActaOutputDto(actaService.findActaById(id)));
    }

    @GetMapping("full/{id}")
    public ResponseEntity<FullActaOutputDto> findFullActaById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new FullActaOutputDto(actaService.findActaById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<ActaOutputDto>> findAll(){
        List<ActaOutputDto> actaOutputDtoList = new ArrayList<>();
        actaService.findAll().forEach(acta -> {
            actaOutputDtoList.add(new ActaOutputDto(acta));
        });
        return ResponseEntity.ok(actaOutputDtoList);
    }

    @GetMapping("getByCompetition/{id}")
    public ResponseEntity<List<ActaOutputDto>> findByCompetition(@PathVariable String id){
        List<ActaOutputDto> actaOutputDtoList = new ArrayList<>();
        actaService.findAllByCompetitionId(id).forEach(acta -> {
            actaOutputDtoList.add(new ActaOutputDto(acta));
        });
        return ResponseEntity.ok(actaOutputDtoList);
    }
}
