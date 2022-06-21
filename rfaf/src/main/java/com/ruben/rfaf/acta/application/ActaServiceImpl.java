package com.ruben.rfaf.acta.application;

import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.acta.infraestructure.repository.ActaRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.goal.domain.Goal;
import com.ruben.rfaf.goal.infraestructure.GoalRepository;
import com.ruben.rfaf.redCard.domain.RedCard;
import com.ruben.rfaf.redCard.infraestructure.RedCardRepository;
import com.ruben.rfaf.yellowCard.domain.YellowCard;
import com.ruben.rfaf.yellowCard.infraestructure.YellowCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActaServiceImpl implements ActaService{

    private final ActaRepository actaRepository;
    private final DesignationRepository designationRepository;
    private final GoalRepository goalRepository;
    private final YellowCardRepository yellowCardRepository;
    private final RedCardRepository redCardRepository;

    @Override
    public Acta create(Acta acta) throws Exception {

        Designation designation = designationRepository
                .findById(acta.getDesignation().getId()).orElseThrow(()-> new Exception("No se ha encontrado la designación"));

        acta.setDesignation(designation);

        return actaRepository.save(acta);
    }

    @Override
    public Acta update(Acta acta, String id) throws Exception {
        Acta actaCheck = actaRepository.findById(id).orElseThrow(()->new Exception("No se ha encotrado el acta"));
        if (acta.getHoraInicio().equals("")||acta.getHoraInicio()==null)
            throw new Exception("No puedes dejar la hora de inico vacía");

        if (acta.getHoraSegundaParte().equals("")||acta.getHoraSegundaParte()==null)
            throw new Exception("No puedes dejar la hora de la segunda parte vacía");

//        if (acta.getStartingLocalPlayers().size()<8)
//            throw new Exception("El equipo local no cumple con el número mínimo de titulares");
//
//        if (acta.getStartingVisitorPlayers().size()<8)
//            throw new Exception("El equipo visitante no cumple con el número mínimo de titulares");
//
//        if (acta.getSubstituteLocalPlayers().size()>7)
//            throw new Exception("El equipo local no cumple con el número máximo de suplentes");
//
//        if (acta.getSubstituteVisitorPlayers().size()>7)
//            throw new Exception("El equipo visitante no cumple con el número máximo de suplentes");

        if (acta.getYellowCards()!=null&&acta.getYellowCards().size()>0){
            acta.getYellowCards().forEach(yellowCard -> {
                YellowCard yellowCardSaved = yellowCardRepository.save(yellowCard);
                yellowCard.setId(yellowCardSaved.getId());
            });
        }

        if (acta.getRedCards()!=null&&acta.getRedCards().size()>0){
            acta.getRedCards().forEach(redCard -> {
                RedCard redCardSaved = redCardRepository.save(redCard);
                redCard.setId(redCardSaved.getId());
            });
        }

        if (acta.getGoalsLocal()!=null&&acta.getGoalsLocal().size()>0){
            acta.getGoalsLocal().forEach(goal -> {
                Goal goalSaved = goalRepository.save(goal);
                goal.setId(goalSaved.getId());
            });
        }

        if (acta.getGoalsVisitor()!=null&&acta.getGoalsVisitor().size()>0){
            acta.getGoalsVisitor().forEach(goal -> {
                Goal goalSaved = goalRepository.save(goal);
                goal.setId(goalSaved.getId());
            });
        }

        acta.setFinalizado(true);
        return actaRepository.save(acta);
    }

    @Override
    public Acta findActaById(String id) throws Exception {
        return actaRepository.findById(id).orElseThrow(()->new Exception("No se ha encontrado el acta"));
    }

    @Override
    public List<Acta> findAll() {

        return actaRepository.findAll();
    }

    @Override
    public List<Acta> findAllByCompetitionId(String id) {

        List<Acta> actaList = new ArrayList<>();
        actaRepository.findAll().forEach(acta -> {
            if (acta.getDesignation().getMatch().getCompetition().getId().equals(id))
                actaList.add(acta);
        });
        return actaList;
    }
}
