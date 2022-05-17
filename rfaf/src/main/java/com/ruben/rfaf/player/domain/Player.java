package com.ruben.rfaf.player.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.player.infrastructure.dto.InputPlayerDTO;
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
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playerSeq")
    @GenericGenerator(
            name = "playerSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PLA"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String licenseNum;
    private String name;
    private String firstname;
    private Date birthDate;
    @ManyToOne
    @JsonIgnore
    private Team team;
    private int number;
    private int sancion;

    public Player(InputPlayerDTO inputPlayerDTO) {
        setId(inputPlayerDTO.getId());
        setLicenseNum(inputPlayerDTO.getLicencia());
        setName(inputPlayerDTO.getName());
        setFirstname(inputPlayerDTO.getFirstname());
        setBirthDate(inputPlayerDTO.getDate());
        setTeam(inputPlayerDTO.getTeam());
        setNumber(inputPlayerDTO.getNumber());
        setSancion(inputPlayerDTO.getSancion());
    }
}
