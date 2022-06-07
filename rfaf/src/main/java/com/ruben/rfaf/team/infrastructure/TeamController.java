package com.ruben.rfaf.team.infrastructure;

import com.ruben.rfaf.team.application.TeamService;
import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.team.infrastructure.dto.InputTeamDTO;
import com.ruben.rfaf.team.infrastructure.dto.OutputTeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // Request para insertar un equipo en la base de datos
    @PostMapping("/create")
    public ResponseEntity<OutputTeamDTO> createTeam(@RequestBody InputTeamDTO inputTeamDTO) throws Exception {
        return ResponseEntity.ok(new OutputTeamDTO(teamService.addTeam(new Team(inputTeamDTO))));
    }

    //Request para leer todos los equipos
    @GetMapping("/getAll")
    public ResponseEntity<List<OutputTeamDTO>> getTeams() {
        List<OutputTeamDTO> outputTeamDTOList = new ArrayList<>();
        for (Team team : teamService.findAll()) {
            outputTeamDTOList.add(new OutputTeamDTO(team));
        }
        return ResponseEntity.ok(outputTeamDTOList);
    }

    //Request para leer un equipo por su ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<OutputTeamDTO> getTeamById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputTeamDTO(teamService.findById(id)));
    }

    //Request para leer un equipo por su ID
    @GetMapping("/getByName/{name}")
    public ResponseEntity<OutputTeamDTO> getTeamByName(@PathVariable String name) throws Exception {
        return ResponseEntity.ok(new OutputTeamDTO(teamService.findByName(name)));
    }

    //Request para actualizar un equipo
    @PostMapping("/update/{id}")
    public ResponseEntity<OutputTeamDTO> updateTeam(@RequestBody InputTeamDTO inputTeamDTO,@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputTeamDTO(teamService.updateTeam(new Team(inputTeamDTO),id)));
    }

    //Request para borrar un equipo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(teamService.deleteById(id));
    }
}
