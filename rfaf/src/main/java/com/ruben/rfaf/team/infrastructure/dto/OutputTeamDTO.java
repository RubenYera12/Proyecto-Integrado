package com.ruben.rfaf.team.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OutputTeamDTO {

    private String id;
    private String name;
    private String image;
    private String coach;
    private List<Player> players;
    private String stadium;
    private Competition competition;

    public OutputTeamDTO(Team team) {
        setId(team.getId());
        setName(team.getName());
        setImage(team.getImage());
        setCoach(team.getCoach());
        setPlayers(team.getPlayers());
        setStadium(team.getStadium());
        setCompetition(team.getCompetition());
    }
}
