package com.ruben.rfaf.match.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.referee.domain.Referee;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputMatchDTO {
    private Competition competition;
    private String season;
    private Date matchDate;
    private Float hour;
    private Team local;
    private Team visitor;
}
