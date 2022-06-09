package com.ruben.rfaf.competition.infrastructure.dto;

import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputCompetitionDTO {

    private String id;
    private String name;
    private String image;
    private String zone;
    private List<Team> teamList;
}
