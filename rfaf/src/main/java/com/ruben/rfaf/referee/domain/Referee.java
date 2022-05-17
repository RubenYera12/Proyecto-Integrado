package com.ruben.rfaf.referee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Referee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refSeq")
    @GenericGenerator(
            name = "refSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "REF"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String licenseNum;
    private String password;
    private String name;
    private String firstname;
    @Column(unique = true)
    private String email;
    private String city;
    private Date birthDate;
    private String image_url;
    private Boolean admin;
    @ManyToOne
    @JsonIgnore
    private Category category;
    private boolean nevera;

    public Referee(InputRefereeDTO userInputDTO) {
        setId(userInputDTO.getId());
        setLicenseNum(userInputDTO.getLicenseNum());
        setPassword(userInputDTO.getPassword());
        setName(userInputDTO.getName());
        setFirstname(userInputDTO.getFirstname());
        setEmail(userInputDTO.getEmail());
        setCity(userInputDTO.getCity());
        setBirthDate(userInputDTO.getBirthDate());
        setImage_url(userInputDTO.getImage_url());
        setAdmin(userInputDTO.getAdmin());
        setCategory(userInputDTO.getCategory());
        setNevera(userInputDTO.isNevera());
    }

}
