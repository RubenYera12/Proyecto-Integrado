package com.ruben.rfaf.team.application;

import com.ruben.rfaf.team.domain.Team;

import java.util.List;

public interface TeamService {
    Team addTeam(Team team) throws Exception;
    Team updateTeam(Team team, String id) throws Exception;

    List<Team> findAll();

    List<Team> noCompetitionTeams();

    String deleteById(String id) throws Exception;
    Team findById(String id) throws Exception;
    Team findByName(String name) throws Exception;

    List<Team> findByCompeticion(String id);

    Team addPlayer(String team_id, String player_id) throws Exception;

    Team deletePlayer(String team_id, String player_id) throws Exception;
}
