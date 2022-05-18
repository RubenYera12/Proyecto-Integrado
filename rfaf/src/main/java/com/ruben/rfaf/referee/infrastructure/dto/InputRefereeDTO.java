package com.ruben.rfaf.referee.infrastructure.dto;

import com.ruben.rfaf.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class InputRefereeDTO {
    private String id;
    private String licenseNum;
    private String password;
    private String name;
    private String firstname;
    private String email;
    private String city="Jaén";
    private Date birthDate;
    private String image_url;
    private Boolean admin;
    private Category category;
    private boolean nevera;
}
