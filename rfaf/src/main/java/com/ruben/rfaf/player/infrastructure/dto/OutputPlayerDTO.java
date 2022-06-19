package com.ruben.rfaf.player.infrastructure.dto;

import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputPlayerDTO {
    private String id;
    private String licencia;
    private String name;
    private String firstname;
    private Date date;
    private Team team;
    private int number;
    private int sancion;
    private String image_url;
    public OutputPlayerDTO(Player player){
        setId(player.getId());
        setLicencia(player.getLicenseNum());
        setName(player.getName());
        setFirstname(player.getFirstname());
        setDate(player.getBirthDate());
        setTeam(player.getTeam());
        setNumber(player.getNumber());
        setSancion(player.getSancion());
        setImage_url(player.getImage_url());
    }
}
