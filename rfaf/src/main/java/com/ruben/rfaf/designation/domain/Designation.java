package com.ruben.rfaf.designation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.designation.infrastructure.dto.InputDesignationDTO;
import com.ruben.rfaf.match.domain.Match;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designationSeq")
    @GenericGenerator(
            name = "designationSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "D"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    @OneToOne
    private Match match;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Referee mainReferee;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Referee assistantReferee1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Referee assistantReferee2;
    private Float priceReferee;
    private Float priceAssistant;
    private String status;

    public Designation(InputDesignationDTO inputDesignationDTO) {
        setId(inputDesignationDTO.getId());
        setMatch(inputDesignationDTO.getMatch());
        setMainReferee(inputDesignationDTO.getMainReferee());
        setAssistantReferee1(inputDesignationDTO.getAssistantReferee1());
        setAssistantReferee2(inputDesignationDTO.getAssistantReferee2());
        setPriceReferee(inputDesignationDTO.getPriceReferee());
        setPriceAssistant(inputDesignationDTO.getPriceReferee());
        setStatus(inputDesignationDTO.getStatus());
    }

}
