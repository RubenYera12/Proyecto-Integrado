package com.ruben.rfaf.acta.infraestructure.dto;

import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.designation.domain.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActaOutputDto {

    private String id;
    private Designation designation;
    private Integer golesLocal;
    private Integer golesVisitante;


    public ActaOutputDto(Acta acta) {
        this.id = acta.getId();
        this.designation = acta.getDesignation();
        this.golesLocal = acta.getGoalsLocal().size();
        this.golesVisitante = acta.getGoalsVisitor().size();
    }
}
