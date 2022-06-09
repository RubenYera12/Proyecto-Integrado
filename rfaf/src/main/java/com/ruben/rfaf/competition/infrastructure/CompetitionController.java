package com.ruben.rfaf.competition.infrastructure;

import com.ruben.rfaf.competition.application.CompetitionService;
import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.competition.infrastructure.dto.InputCompetitionDTO;
import com.ruben.rfaf.competition.infrastructure.dto.OutputCompetitionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/competition")
@AllArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;

    @PostMapping("create")
    public ResponseEntity<OutputCompetitionDTO> create(@RequestBody InputCompetitionDTO inputCompetitionDTO) throws Exception {
        return ResponseEntity.ok(new OutputCompetitionDTO(competitionService.createCompetition(new Competition(inputCompetitionDTO))));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<OutputCompetitionDTO> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputCompetitionDTO(competitionService.findById(id)));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<OutputCompetitionDTO>> findAll() {
        List<OutputCompetitionDTO> outputCompetitionDTOList = new ArrayList<>();
        for (Competition competition : competitionService.findAll()) {
            outputCompetitionDTOList.add(new OutputCompetitionDTO(competition));
        }
        return ResponseEntity.ok(outputCompetitionDTOList);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
        competitionService.deleteById(id);
        return ResponseEntity.ok("Se ha borrado la competicion correctamente");
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OutputCompetitionDTO> update(@PathVariable String id, @RequestBody InputCompetitionDTO inputCompetitionDTO) throws Exception {
        return ResponseEntity.ok(new OutputCompetitionDTO(competitionService.update(new Competition(inputCompetitionDTO), id)));
    }
}
