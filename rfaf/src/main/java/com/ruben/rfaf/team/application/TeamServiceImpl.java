package com.ruben.rfaf.team.application;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.competition.infrastructure.repository.CompetitionRepository;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.player.infrastructure.repository.PlayerRepository;
import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.team.infrastructure.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final CompetitionRepository competitionRepository;

    @Override
    public Team addTeam(Team team) throws Exception {
        Optional<Team> teamOptional = teamRepository.findByNameIgnoreCase(team.getName());
        if (teamOptional.isPresent())
            throw new Exception("El equipo: " + team.getName() + " ya existe");

        team.getPlayers().forEach(player -> {
            Player playerFind = playerRepository.findById(player.getId()).get();
            playerFind.setTeam(team);
            playerRepository.save(playerFind);
        });

        Competition competition = competitionRepository
                .findById(team.getCompetition().getId())
                .orElseThrow(() -> new Exception("No se ha encontrado la competición"));
        if (!competition.getTeamList().contains(team)) {
            competition.getTeamList().add(team);
            competitionRepository.save(competition);
        }
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team team, String id) throws Exception {
        Team teamOptional = teamRepository.findById(id).orElseThrow(() -> new Exception("No se ha podido encontrar el equipo."));
        if (!teamOptional.getName().equals(team.getName()))
            throw new Exception("No se puede cambiar el Nombre del Equipo");
        // TODO: Comprobar campos nulos
        if (team.getStadium() == null || team.getStadium().equals(""))
            throw new Exception("No puedes dejar al club sin estadio");
        if (team.getImage() == null || team.getImage().equals(""))
            team.setImage(teamOptional.getImage());
        team.setId(id);

        team.getPlayers().forEach(player -> {
            Player playerFind = playerRepository.findById(player.getId()).get();
            playerFind.setTeam(team);
            playerRepository.save(playerFind);
        });

        Competition competition = competitionRepository
                .findById(team.getCompetition().getId())
                .orElseThrow(() -> new Exception("No se ha encontrado la competición"));
        boolean comp = false;
        for(Team team1:competition.getTeamList()) {
            if(team1.getId().equalsIgnoreCase(team.getId()))
                comp=true;
        }
        if (!comp){
            competition.getTeamList().add(team);
            competitionRepository.save(competition);
        }

        return teamRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public List<Team> noCompetitionTeams() {
        return teamRepository.findByCompetitionId(null);
    }

    @Override
    public String deleteById(String id) throws Exception {
        try {
            teamRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Fallo inesperado");
        }

        return "Se ha borrado correctamente al equipo";
    }

    @Override
    public Team findById(String id) throws Exception {
        return teamRepository
                .findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
    }

    @Override
    public Team findByName(String name) throws Exception {
        return teamRepository
                .findByNameIgnoreCase(name).orElseThrow(() -> new Exception("No se ha podido encontrar el equipo"));
    }

    @Override
    public Team addPlayer(String team_id, String player_id) throws Exception {
        Team team = teamRepository.findById(team_id).orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
        Player player = playerRepository.findById(player_id).orElseThrow(() -> new Exception("No se ha encontrado el jugador"));
        if (player.getTeam() != null)
            throw new Exception("El jugador ya tiene un equipo asociado");
        team.getPlayers().add(player);
        player.setTeam(team);
        return teamRepository.save(team);
    }

    @Override
    public Team deletePlayer(String team_id, String player_id) throws Exception {
        Team team = teamRepository.findById(team_id).orElseThrow(() -> new Exception("No se ha encontrado el equipo"));
        Player player = playerRepository.findById(player_id).orElseThrow(() -> new Exception("No se ha encontrado el jugador"));
        if (player.getTeam() == null)
            throw new Exception("El jugador no tiene un equipo asociado");
        team.getPlayers().remove(player);
        player.setTeam(null);
        return teamRepository.save(team);
    }
}
