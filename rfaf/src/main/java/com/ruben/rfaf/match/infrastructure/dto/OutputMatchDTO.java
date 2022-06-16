package com.ruben.rfaf.match.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputMatchDTO {

    private String id;
    private Competition competition;
    private String season;
    private Date matchDate;
    private Float hour;
    private Team local;
    private Team visitor;

    public OutputMatchDTO(Match match){
        setId(match.getId());
        setCompetition(match.getCompetition());
        setSeason(match.getSeason());
        setMatchDate(match.getFecha());
        setHour(match.getHora());
        setLocal(match.getLocal());
        setVisitor(match.getVisitor());
    }
}
