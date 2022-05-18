package com.ruben.rfaf.match.infrastructure;

import com.ruben.rfaf.match.application.MatchService;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.match.infrastructure.dto.InputMatchDTO;
import com.ruben.rfaf.match.infrastructure.dto.OutputMatchDTO;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/match")
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("create")
    public OutputMatchDTO addGame(@RequestBody InputMatchDTO inputMatchDTO) {
        return new OutputMatchDTO(matchService.createGame(new Match(inputMatchDTO)));
    }

    @GetMapping("findById/{id}")
    public OutputMatchDTO findById(@PathVariable String id) throws Exception {
        return new OutputMatchDTO(matchService.findById(id));
    }

    @GetMapping("findAll")
    public List<OutputMatchDTO> findAll() {
        List<OutputMatchDTO> outputMatchDTOList = new ArrayList<>();
        for (Match match : matchService.findAll()) {
            outputMatchDTOList.add(new OutputMatchDTO(match));
        }
        return outputMatchDTOList;
    }

    //TODO: Cambiar endpoint a Designation
    @PutMapping("assign/cancelAssignment")
    public OutputMatchDTO cancelAssignment(@RequestParam String match_id) throws Exception {
        return new OutputMatchDTO(matchService.cancelAssignment(match_id));
    }

    //TODO: Cambiar endpoint a Designation
    @PutMapping("assign/assingMatch")
    public OutputMatchDTO assingMatch(@RequestParam String match_id, @RequestParam String referee_id) throws Exception {
        return new OutputMatchDTO(matchService.assignMatch(match_id,referee_id));
    }
}
