package com.ruben.rfaf.acta.infraestructure.dto;

import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.goal.domain.Goal;
import com.ruben.rfaf.player.domain.Player;
import com.ruben.rfaf.redCard.domain.RedCard;
import com.ruben.rfaf.yellowCard.domain.YellowCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActaInputDto {
    private String id;
    private Designation designation;
    private String horaInicio;
    private String horaSegundaParte;
    private Boolean desfibrilador=false;
    private List<Player> startingLocalPlayers;
    private List<Player> substituteLocalPlayers;
    private List<Player> startingVisitorPlayers;
    private List<Player> substituteVisitorPlayers;
    private List<YellowCard> yellowCards;
    private List<RedCard> redCards;
    private List<Goal> goalsLocal;
    private List<Goal> goalsVisitor;
    private String publico;
    private String deficienciasTerreno;
    private String otrasObservaciones;

}
