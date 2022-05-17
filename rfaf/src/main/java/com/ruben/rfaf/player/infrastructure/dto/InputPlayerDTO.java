package com.ruben.rfaf.player.infrastructure.dto;

import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputPlayerDTO {
    private String id;
    private String licencia;
    private String name;
    private String firstname;
    private Date date;
    private Team team;
    private int number;
    private int sancion;
}
