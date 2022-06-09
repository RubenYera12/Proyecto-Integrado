package com.ruben.rfaf.competition.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.competition.infrastructure.dto.InputCompetitionDTO;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compSeq")
    @GenericGenerator(
            name = "compSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "COM"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String name;
    private String image;
    private String zone;
    @OneToMany
    @JsonIgnore
    private List<Match> matchList;
    @OneToMany
    @JsonIgnore
    private List<Team> teamList;

    public Competition(InputCompetitionDTO inputCompetitionDTO){
        setId(inputCompetitionDTO.getId());
        setName(inputCompetitionDTO.getName());
        setImage(inputCompetitionDTO.getImage());
        setZone(inputCompetitionDTO.getZone());
        setTeamList(inputCompetitionDTO.getTeamList());
    }

}
