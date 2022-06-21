package com.ruben.rfaf.referee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.category.infrastructure.repository.CategoryRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.referee.infrastructure.dto.InputRefereeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Column(unique = true)
    private String licenseNum;
    private String password="1234";
    private String name;
    private String firstname;
    @Column(unique = true)
    private String email;
    private String telfNumber;
    private String city = "Jaén";
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String image_url="defaultReferee.jpg";
    private Boolean admin=false;
    @ManyToOne
    @JsonIgnore
    private Category category;
    private boolean nevera=false;
    @OneToMany
    private List<Designation> designationList=new ArrayList<>();

    public Referee(InputRefereeDTO userInputDTO) throws Exception {
        setLicenseNum(userInputDTO.getLicenseNum());
        setPassword(userInputDTO.getPassword());
        setName(userInputDTO.getName());
        setFirstname(userInputDTO.getFirstname());
        setEmail(userInputDTO.getEmail());
        setTelfNumber(userInputDTO.getTelfNumber());
        setCity(userInputDTO.getCity());
        setBirthDate(userInputDTO.getBirthDate());
        setImage_url(userInputDTO.getImage_url());
        setAdmin(userInputDTO.getAdmin());
        setNevera(userInputDTO.isNevera());
    }

    public String getNombreCompleto() {
        return getFirstname() + ", " + getName();
    }

}
