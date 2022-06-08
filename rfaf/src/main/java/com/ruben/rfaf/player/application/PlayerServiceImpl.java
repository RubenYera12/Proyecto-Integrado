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
    public List<Player> findNoTeamPlayers() {

        return playerRepository.findByTeamId(null);
    }

    @Override
    public Player updatePlayer(Player player, String id) throws Exception {
        Player optionalPlayer = playerRepository
                .findById(id)
                .orElseThrow(()-> new Exception("No se ha podido encontrar el jugador."));
        if (!optionalPlayer.getLicenseNum().equals(player.getLicenseNum())) {
            throw new Exception("No se puede cambiar el número de licencia");
        }
        if (player.getName()==null||player.getName().equals("")) {
            throw new Exception("No se puede dejar sin nombre al jugador");
        }
        if (player.getFirstname()==null||player.getFirstname().equals("")) {
            throw new Exception("No se puede dejar sin apellido al jugador");
        }
        // TODO: Comprobar campos nulos
        player.setId(id);
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
                .findByNameIgnoreCase(name)
                .orElseThrow(() -> new Exception("No se ha encontrado el equipo: " + name))
                .getPlayers();
    }

}
