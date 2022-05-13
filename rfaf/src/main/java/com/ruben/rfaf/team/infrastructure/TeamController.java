package com.ruben.rfaf.team.infrastructure;

import com.ruben.rfaf.team.application.TeamService;
import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.team.infrastructure.dto.InputTeamDTO;
import com.ruben.rfaf.team.infrastructure.dto.OutputTeamDTO;
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
    public OutputTeamDTO createTeam(@RequestBody InputTeamDTO inputTeamDTO) throws Exception {
        return new OutputTeamDTO(teamService.addTeam(new Team(inputTeamDTO)));
    }

    //Request para leer todos los equipos
    @GetMapping("/getAll")
    public List<OutputTeamDTO> getTeams() {
        List<OutputTeamDTO> outputTeamDTOList = new ArrayList<>();
        for (Team team : teamService.findAll()) {
            outputTeamDTOList.add(new OutputTeamDTO(team));
        }
        return outputTeamDTOList;
    }

    //Request para leer un equipo por su ID
    @GetMapping("/getById/{id}")
    public OutputTeamDTO getTeamById(@PathVariable String id) throws Exception {
        return new OutputTeamDTO(teamService.findById(id));
    }

    //Request para leer un equipo por su ID
    @GetMapping("/getByName/{name}")
    public OutputTeamDTO getTeamByName(@PathVariable String name) throws Exception {
        return new OutputTeamDTO(teamService.findByName(name));
    }

    //Request para actualizar un equipo
    @PostMapping("/update")
    public OutputTeamDTO updateTeam(@RequestBody InputTeamDTO inputTeamDTO) throws Exception {
        return new OutputTeamDTO(teamService.updateTeam(new Team(inputTeamDTO)));
    }

    //Request para borrar un equipo
    @DeleteMapping("/delete/{id}")
    public String deleteTeam(@PathVariable String id) throws Exception {
        return teamService.deleteById(id);
    }
}
