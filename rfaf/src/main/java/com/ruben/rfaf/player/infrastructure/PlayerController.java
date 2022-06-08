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
@RequestMapping("api/players")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("create")
    public ResponseEntity<OutputPlayerDTO> createPlayer(@RequestBody InputPlayerDTO inputPlayerDTO) throws Exception {
        return ResponseEntity.ok(new OutputPlayerDTO(playerService.addPlayer(new Player(inputPlayerDTO))));
    }

    @PostMapping("update/{id}")
    public ResponseEntity<OutputPlayerDTO> updatePlayer(@RequestBody InputPlayerDTO inputPlayerDTO,@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputPlayerDTO(playerService.updatePlayer(new Player(inputPlayerDTO),id)));
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

    @GetMapping("findAll")
    public ResponseEntity<List<OutputPlayerDTO>> findAll() throws Exception {
        List<OutputPlayerDTO> outputPlayerDTOList = new ArrayList<>();
        for (Player player: playerService.findAll() ) {
            outputPlayerDTOList.add(new OutputPlayerDTO(player));
        }
        return ResponseEntity.ok(outputPlayerDTOList);
    }

    @GetMapping("findNoTeamPlayers")
    public ResponseEntity<List<OutputPlayerDTO>> findNoTeamPlayers() {
        List<OutputPlayerDTO> outputPlayerDTOList = new ArrayList<>();
        for (Player player: playerService.findNoTeamPlayers() ) {
            outputPlayerDTOList.add(new OutputPlayerDTO(player));
        }
        return ResponseEntity.ok(outputPlayerDTOList);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(playerService.deleteById(id));
    }
}
