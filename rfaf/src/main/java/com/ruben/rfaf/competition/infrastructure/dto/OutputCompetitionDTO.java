package com.ruben.rfaf.competition.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputCompetitionDTO {
    private String id;
    private String name;
    private String image;
    private String zone;
    private List<Team> teams;

    public OutputCompetitionDTO(Competition competition){
        setId(competition.getId());
        setName(competition.getName());
        setImage(competition.getImage());
        setZone(competition.getZone());
        setTeams(competition.getTeamList());
    }

}
