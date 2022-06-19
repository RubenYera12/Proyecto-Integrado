package com.ruben.rfaf.match.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.match.infrastructure.dto.InputMatchDTO;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="partidos")
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
    private Competition competition;
    private String season;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Float hora;
    @ManyToOne

    private Team local;
    @ManyToOne
    private Team visitor;

    public Match(InputMatchDTO inputMatchDTO) {
        setId(inputMatchDTO.getId());
        setCompetition(inputMatchDTO.getCompetition());
        setSeason(inputMatchDTO.getSeason());
        setFecha(inputMatchDTO.getMatchDate());
        setHora(inputMatchDTO.getHour());
        setLocal(inputMatchDTO.getLocal());
        setVisitor(inputMatchDTO.getVisitor());
    }
}
