package com.ruben.rfaf.match.infrastructure;

import com.ruben.rfaf.match.application.MatchService;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.match.infrastructure.dto.InputMatchDTO;
import com.ruben.rfaf.match.infrastructure.dto.OutputMatchDTO;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/match")
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("create")
    public ResponseEntity<OutputMatchDTO> addGame(@RequestBody InputMatchDTO inputMatchDTO) {
        return ResponseEntity.ok(new OutputMatchDTO(matchService.createGame(new Match(inputMatchDTO))));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<OutputMatchDTO> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputMatchDTO(matchService.findById(id)));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<OutputMatchDTO>> findAll() {
        List<OutputMatchDTO> outputMatchDTOList = new ArrayList<>();
        for (Match match : matchService.findAll()) {
            outputMatchDTOList.add(new OutputMatchDTO(match));
        }
        return ResponseEntity.ok(outputMatchDTOList);
    }
}
