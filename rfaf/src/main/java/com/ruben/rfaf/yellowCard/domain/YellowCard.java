package com.ruben.rfaf.yellowCard.domain;

import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YellowCard {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Player player;

    private Integer minuto;
    private String motivo;
}
