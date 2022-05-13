package com.ruben.rfaf.player.infrastructure;

import com.ruben.rfaf.player.application.PlayerService;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.player.infrastructure.dto.InputPlayerDTO;
import com.ruben.rfaf.player.infrastructure.dto.OutputPlayerDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("player")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/createPlayer")
    public OutputPlayerDTO createPlayer(@RequestBody InputPlayerDTO inputPlayerDTO) throws Exception {
        return new OutputPlayerDTO(playerService.addPlayer(new Player(inputPlayerDTO)));
    }

    @GetMapping("findById/{id}")
    public OutputPlayerDTO findById(@PathVariable String id) throws Exception {
        return new OutputPlayerDTO(playerService.findById(id));
    }

    @GetMapping("findByLicense/{license}")
    public OutputPlayerDTO findByLicense(@PathVariable String license) throws Exception {
        return new OutputPlayerDTO(playerService.findByLicense(license));
    }

    @GetMapping("findByTeam/{name}")
    public List<OutputPlayerDTO> findByTeam(@PathVariable String name) throws Exception {
        List<OutputPlayerDTO> outputPlayerDTOList = new ArrayList<>();
        for (Player player: playerService.findByTeam(name) ) {
            outputPlayerDTOList.add(new OutputPlayerDTO(player));
        }
        return outputPlayerDTOList;
    }
}
