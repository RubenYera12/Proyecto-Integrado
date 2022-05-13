package com.ruben.rfaf.referee.infrastructure.dto;

import com.ruben.rfaf.category.domain.Category;
import com.ruben.rfaf.referee.domain.Referee;
import lombok.Data;

import java.util.Date;

@Data
public class OutputRefereeDTO {
    private String id;
    private String licenseNum;
    private String name;
    private String firstname;
    private String email;
    private String city;
    private Date birthDate;
    private String image_url;
    private Boolean admin;
    private Category category;

    public OutputRefereeDTO(Referee user){
        setId(user.getId());
        setLicenseNum(user.getLicenseNum());
        setName(user.getName());
        setFirstname(user.getFirstname());
        setEmail(user.getEmail());
        setCity(user.getCity());
        setBirthDate(user.getBirthDate());
        setImage_url(user.getImage_url());
        setAdmin(user.getAdmin());
        setCategory(user.getCategory());
    }
}
