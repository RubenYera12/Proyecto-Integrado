package com.ruben.rfaf.team.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InputTeamDTO {
    private String name;
    private String image;
    private String coach;
    private String stadium;
    private Competition competition;
}
