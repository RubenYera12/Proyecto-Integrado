package com.ruben.rfaf.team.application;

import com.ruben.rfaf.team.domain.Team;

import java.util.List;

public interface TeamService {
    Team addTeam(Team team) throws Exception;
    Team updateTeam(Team team) throws Exception;
    List<Team> findAll();
    String deleteById(String id) throws Exception;
    Team findById(String id) throws Exception;
    Team findByName(String name) throws Exception;
}
