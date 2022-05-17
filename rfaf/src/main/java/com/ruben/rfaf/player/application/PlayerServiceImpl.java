package com.ruben.rfaf.player.application;

import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.player.infrastructure.repository.PlayerRepository;
import com.ruben.rfaf.team.infrastructure.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Override
    public Player addPlayer(Player player) throws Exception {
        Optional<Player> optionalPlayer = playerRepository.findByLicenseNum(player.getLicenseNum());
        if (optionalPlayer.isPresent()) {
            throw new Exception("El jugador con licencia: " + player.getLicenseNum() + " ya existe");
        } else return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) throws Exception {
        if (player.getId() == null) {
            throw new Exception("No se ha podido encontrar el jugador.");
        }
        Optional<Player> optionalPlayer = playerRepository.findById(player.getId());
        if (!optionalPlayer.get().getLicenseNum().equals(player.getLicenseNum())) {
            throw new Exception("No se puede cambiar el número de licencia");
        }
        // TODO: Comprobar campos nulos
        return playerRepository.save(player);
    }

    @Override
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Override
    public String deleteById(String id) throws Exception {
        try {
            playerRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Fallo inesperado");
        }
        return "Se ha borrado correctamente al Jugador";
    }

    @Override
    public Player findById(String id) throws Exception {
        return playerRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el jugador"));
    }

    @Override
    public Player findByLicense(String license) throws Exception {
        return playerRepository
                .findByLicenseNum(license)
                .orElseThrow(() -> new Exception("No se ha podido encontrar el jugador con licencia: " + license));
    }

    @Override
    public List<Player> findByTeam(String name) throws Exception {
        return teamRepository
                .findByName(name)
                .orElseThrow(() -> new Exception("No se ha encontrado el equipo: " + name))
                .getPlayers();
    }
}
