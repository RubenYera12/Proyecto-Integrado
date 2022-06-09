package com.ruben.rfaf.player.application;

import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.player.infrastructure.repository.PlayerRepository;
import com.ruben.rfaf.team.domain.Team;
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
        Optional<Player> playerCheck = playerRepository.findByLicenseNum(player.getLicenseNum());
        if (playerCheck.isPresent())
            throw new Exception("El jugador con licencia: " + player.getLicenseNum() + " ya existe");
        //TODO:Comprobar null
        if(player.getBirthDate()==null){
            throw new Exception("No se ha introducido fecha de nacimiento");
        }
        if (player.getNumber()<=0||player.getNumber()>99){
            throw new Exception("Introduce un dorsal entre 1 y 99");
        }
        if (player.getTeam().getId() != null && !player.getTeam().getId().equals("")) {
            Team team = teamRepository.findById(player.getTeam().getId()).orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
            player.setTeam(team);
            playerRepository.save(player);
            team.getPlayers().add(player);
            teamRepository.save(team);
            return player;
        }
        player.setTeam(null);
        return playerRepository.save(player);
    }

    @Override
    public List<Player> findNoTeamPlayers() {

        return playerRepository.findByTeamId(null);
    }

    @Override
    public Player updatePlayer(Player player, String id) throws Exception {
        Player optionalPlayer = playerRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha podido encontrar el jugador."));
        player.setId(id);
        if (!optionalPlayer.getLicenseNum().equals(player.getLicenseNum())) {
            throw new Exception("No se puede cambiar el número de licencia");
        }
        if (player.getName() == null || player.getName().equals("")) {
            throw new Exception("No se puede dejar sin nombre al jugador");
        }
        if (player.getFirstname() == null || player.getFirstname().equals("")) {
            throw new Exception("No se puede dejar sin apellido al jugador");
        }

        // TODO: Comprobar campos nulos

        if (player.getTeam().getId() == null || player.getTeam().getId().equals("")) {
            player.setTeam(null);
            Team teamCheck = optionalPlayer.getTeam();
            if (teamCheck != null) {
                for (Player player1 : teamCheck.getPlayers()) {
                    if (player1.getId().equals(player.getId())) {
                        teamCheck.getPlayers().remove(player1);
                        teamRepository.save(teamCheck);
                        return playerRepository.save(player);
                    }
                }
            }
        } else {
            Team team = teamRepository.findById(player.getTeam().getId()).orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
            for (Player player1 : team.getPlayers()) {
                if (player.getId().equals(player1.getId())) {
                    return playerRepository.save(player);
                }
            }
            team.getPlayers().add(player);
            teamRepository.save(team);
        }
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
