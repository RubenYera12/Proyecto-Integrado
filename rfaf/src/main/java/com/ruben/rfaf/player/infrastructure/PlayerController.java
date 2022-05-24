package com.ruben.rfaf.player.infrastructure;

import com.ruben.rfaf.player.application.PlayerService;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.player.infrastructure.dto.InputPlayerDTO;
import com.ruben.rfaf.player.infrastructure.dto.OutputPlayerDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("player")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/createPlayer")
    public ResponseEntity<OutputPlayerDTO> createPlayer(@RequestBody InputPlayerDTO inputPlayerDTO) throws Exception {
        return ResponseEntity.ok(new OutputPlayerDTO(playerService.addPlayer(new Player(inputPlayerDTO))));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<OutputPlayerDTO> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputPlayerDTO(playerService.findById(id)));
    }

    @GetMapping("findByLicense/{license}")
    public ResponseEntity<OutputPlayerDTO> findByLicense(@PathVariable String license) throws Exception {
        return ResponseEntity.ok(new OutputPlayerDTO(playerService.findByLicense(license)));
    }

    @GetMapping("findByTeam/{name}")
    public ResponseEntity<List<OutputPlayerDTO>> findByTeam(@PathVariable String name) throws Exception {
        List<OutputPlayerDTO> outputPlayerDTOList = new ArrayList<>();
        for (Player player: playerService.findByTeam(name) ) {
            outputPlayerDTOList.add(new OutputPlayerDTO(player));
        }
        return ResponseEntity.ok(outputPlayerDTOList);
    }
}
