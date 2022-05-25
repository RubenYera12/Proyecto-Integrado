package com.ruben.rfaf.team.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.competition.domain.Competition;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.team.infrastructure.dto.InputTeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSeq")
    @GenericGenerator(
            name = "teamSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "TEAM"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String name;
    private String image;
    private String coach;
    @OneToMany
    @JsonIgnore
    private List<Player> players;
    private String stadium;
    @ManyToOne
    @JsonIgnore
    private Competition competition;

    public Team(InputTeamDTO inputTeamDTO) {
        setName(inputTeamDTO.getName());
        setImage(inputTeamDTO.getImage());
        setCoach(inputTeamDTO.getCoach());
        setStadium(inputTeamDTO.getStadium());
        setCompetition(inputTeamDTO.getCompetition());
    }

}
