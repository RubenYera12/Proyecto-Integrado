package com.ruben.rfaf.competition.infrastructure.dto;

import com.ruben.rfaf.competition.domain.Competition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputCompetitionDTO {

    private String name;
    private String zone;

    public OutputCompetitionDTO(Competition competition){
        setName(competition.getName());
        setZone(competition.getZone());
    }

}
