package com.ruben.rfaf.acta.domain;

import com.ruben.rfaf.StringPrefixedSequenceIdGenerator;
import com.ruben.rfaf.goal.domain.Goal;
import com.ruben.rfaf.redCard.domain.RedCard;
import com.ruben.rfaf.yellowCard.domain.YellowCard;
import com.ruben.rfaf.acta.infraestructure.dto.ActaInputDto;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Acta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actaSeq")
    @GenericGenerator(
            name = "actaSeq",
            strategy = "com.ruben.rfaf.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ACT"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    @OneToOne
    private Designation designation;
    private String horaInicio;
    private String horaSegundaParte;
    private Boolean desfibrilador=false;
    @ManyToMany
    private List<Player> startingLocalPlayers;
    @ManyToMany
    private List<Player> substituteLocalPlayers;
    @ManyToMany
    private List<Player> startingVisitorPlayers;
    @ManyToMany
    private List<Player> substituteVisitorPlayers;
    @OneToMany
    private List<YellowCard> yellowCards;
    @OneToMany
    private List<RedCard> redCards;
    @OneToMany
    private List<Goal> goalsLocal;
    @OneToMany
    private List<Goal> goalsVisitor;
    private String publico;
    private String deficienciasTerreno;
    private String otrasObservaciones;

    private Boolean finalizado;

    public Acta(ActaInputDto actaInputDto){
        this.id=actaInputDto.getId();
        this.designation=actaInputDto.getDesignation();
        this.horaInicio= actaInputDto.getHoraInicio();
        this.horaSegundaParte=actaInputDto.getHoraSegundaParte();
        this.desfibrilador=actaInputDto.getDesfibrilador();
        this.startingLocalPlayers=actaInputDto.getStartingLocalPlayers();
        this.startingVisitorPlayers=actaInputDto.getStartingVisitorPlayers();
        this.substituteLocalPlayers=actaInputDto.getSubstituteLocalPlayers();
        this.substituteVisitorPlayers=actaInputDto.getSubstituteVisitorPlayers();
        this.yellowCards=actaInputDto.getYellowCards();
        this.redCards=actaInputDto.getRedCards();
        this.goalsLocal=actaInputDto.getGoalsLocal();
        this.goalsVisitor=actaInputDto.getGoalsVisitor();
        this.publico=actaInputDto.getPublico();
        this.deficienciasTerreno=actaInputDto.getDeficienciasTerreno();
        this.otrasObservaciones=actaInputDto.getOtrasObservaciones();
    }
}
