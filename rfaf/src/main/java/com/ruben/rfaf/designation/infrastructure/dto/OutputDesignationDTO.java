package com.ruben.rfaf.designation.infrastructure.dto;

import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDesignationDTO {

    private Match match;
    private Referee mainReferee;
    private Referee assistantReferee1;
    private Referee assistantReferee2;
    private Float priceReferee;
    private Float priceAssistant;


    public OutputDesignationDTO(Designation designation){
        setMatch(designation.getMatch());
        setMainReferee(designation.getMainReferee());
        setAssistantReferee1(designation.getAssistantReferee1());
        setAssistantReferee2(designation.getAssistantReferee2());
        setPriceReferee(designation.getPriceReferee());
        setPriceAssistant(designation.getPriceAssistant());
    }

}
