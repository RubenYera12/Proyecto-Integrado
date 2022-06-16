package com.ruben.rfaf.team.application;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.competition.infrastructure.repository.CompetitionRepository;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.match.infrastructure.repository.MatchRepository;
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
    private final MatchRepository matchRepository;
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

        if (team.getCompetition().getId()==null||team.getCompetition().getId().equals("")){
            team.setCompetition(null);
            teamRepository.save(team);
        }else {
            Competition competition = competitionRepository
                    .findById(team.getCompetition().getId())
                    .orElseThrow(() -> new Exception("No se ha encontrado la competición"));
            boolean comp = false;
            for (Team team1 : competition.getTeamList()) {
                if (team1.getId().equalsIgnoreCase(team.getId()))
                    comp = true;
            }
            if (!comp) {
                team.setCompetition(competition);
                teamRepository.save(team);
                competition.getTeamList().add(team);
                competitionRepository.save(competition);
            }
        }
        return team;
    }

    @Override
    public Team updateTeam(Team team, String id) throws Exception {
        //Comprobamos que exista el equipo
        Team teamOptional = teamRepository.findById(id).orElseThrow(() -> new Exception("No se ha podido encontrar el equipo."));

        //Comprobamos el nombre del equipo
        if (!teamOptional.getName().equals(team.getName()))
            throw new Exception("No se puede cambiar el Nombre del Equipo");

        //Comprobamos el estadio
        if (team.getStadium() == null || team.getStadium().equals(""))
            throw new Exception("No puedes dejar al club sin estadio");

        //Comprobamos la imagen
        if (team.getImage() == null || team.getImage().equals(""))
            team.setImage(teamOptional.getImage());
        team.setId(id);

        //Comprobamos los jugadores
        team.getPlayers().forEach(player -> {
            Player playerFind = playerRepository.findById(player.getId()).get();
            playerFind.setTeam(team);
            playerRepository.save(playerFind);
        });

        if (team.getCompetition().getId()==null||team.getCompetition().getId().equals("")){
            if(teamOptional.getCompetition()!=null){
                Optional<Competition> competition = competitionRepository.findById(teamOptional.getCompetition().getId());
                if(competition.isPresent())
                    for (Team team1 : competition.get().getTeamList()) {
                        if (team1.getId().equalsIgnoreCase(team.getId())){
                            competition.get().getTeamList().remove(team1);
                            competitionRepository.save(competition.get());
                        }

                    }
            }
            team.setCompetition(null);
        }else {
            Competition competition = competitionRepository
                    .findById(team.getCompetition().getId())
                    .orElseThrow(() -> new Exception("No se ha encontrado la competición"));
            boolean comp = false;
            for (Team team1 : competition.getTeamList()) {
                if (team1.getId().equalsIgnoreCase(team.getId()))
                    comp = true;
            }
            if (!comp) {
                competition.getTeamList().add(team);
                competitionRepository.save(competition);
            }
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
        Team team = teamRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado el equipo"));

        List<Match> matchList = matchRepository.findByTeamId(id);
        if (matchList.size()>0)
            throw new Exception("No se puede borrar el equipo, ya que tiene partidos asociados.");

        Competition competition = competitionRepository
                .findById(team.getCompetition().getId())
                .orElseThrow(() -> new Exception("No se ha encontrado la competición"));
        boolean comp = false;
        for(Team team1:competition.getTeamList()) {
            if(team1.getId().equalsIgnoreCase(team.getId())){
                competition.getTeamList().remove(team1);
                competitionRepository.save(competition);
                break;
            }

        }
        for (Player player:team.getPlayers() ) {
            player.setTeam(null);
            playerRepository.save(player);
        }

        teamRepository.deleteById(id);
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
    public List<Team> findByCompeticion(String id){
        return teamRepository.findByCompetitionId(id);
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
