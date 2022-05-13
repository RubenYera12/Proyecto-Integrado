package com.ruben.rfaf.match.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.match.infrastructure.dto.InputMatchDTO;
import com.ruben.rfaf.team.domain.Team;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matchSeq")
    @GenericGenerator(
            name = "matchSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "MATCH"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    @ManyToOne
    @JsonIgnore
    private Competition competition;
    private String season;
    @ManyToOne
    @JsonIgnore
    private Team local;
    @ManyToOne
    @JsonIgnore
    private Team visitor;
    @ManyToOne
    @JsonIgnore
    private Referee referee;

    public Match(InputMatchDTO inputMatchDTO) {
        setId(inputMatchDTO.getId());
        setCompetition(inputMatchDTO.getCompetition());
        setSeason(inputMatchDTO.getSeason());
        setLocal(inputMatchDTO.getLocal());
        setVisitor(inputMatchDTO.getVisitor());
        setReferee(inputMatchDTO.getReferee());
    }
}
