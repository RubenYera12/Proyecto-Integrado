package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.acta.application.ActaService;
import com.ruben.rfaf.acta.domain.Acta;
import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.match.infrastructure.repository.MatchRepository;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import com.ruben.rfaf.shared.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final MatchRepository matchRepository;
    private final RefereeRepository refereeRepository;
    private final EmailService emailService;
    private final ActaService actaService;

    @Override
    public Designation findById(String id) throws Exception {
        return designationRepository.findById(id).orElseThrow(() -> new Exception("No se ha encontrado la designación"));
    }

    @Override
    public List<Designation> findAll() {
        return designationRepository.findAll();
    }

    @Override
    public void remove(String id) throws Exception {
        Designation designationCheck = designationRepository.findById(id).orElseThrow(() -> new Exception("No se ha encontrado la designación"));
        designationRepository.deleteById(id);
    }

    @Override
    public Designation assign(Designation designation) throws Exception {
        //Comprobamos los datos de la designacion
        designation.setMatch(matchRepository
                .findById(designation.getMatch().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el partido.")));

        //Comprobamos que el partido seleccionado no tenga una designacion asociada
//        Optional<Designation> designationCheck = designationRepository.findByMatchId(designation.getMatch().getId());
//        if (designationCheck.isPresent())
//            throw new Exception("El partido seleccionado ya tiene una designación asociada");

        //Comprobamos que los arbitros no esten repetidos
        if (designation.getMainReferee().getEmail().equals(designation.getAssistantReferee1().getEmail()) ||
                designation.getMainReferee().getEmail().equals(designation.getAssistantReferee2().getEmail()) ||
                designation.getAssistantReferee1().getEmail().equals(designation.getAssistantReferee2().getEmail())) {
            throw new Exception("Arbitro repetido.");
        }
        designation.setMainReferee(refereeRepository
                .findById(designation.getMainReferee().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el arbitro.")));
        designation.setAssistantReferee1(refereeRepository
                .findById(designation.getAssistantReferee1().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el asistente 1.")));
        designation.setAssistantReferee2(refereeRepository
                .findById(designation.getAssistantReferee2().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el asistente 2.")));

        //Comprobamos que esté aceptada
        if (designation.getStatus() != null) {
            if (designation.getStatus().equals("ACEPTADA"))
                throw new Exception("La designacion ya ha sido aceptada.");
        }
        designation.setStatus("ACEPTADA");

        designation = designationRepository.save(designation);
        Acta acta = new Acta();
        acta.setDesignation(designation);
        actaService.create(acta);

        emailService.emailDesignacionArbitro(designation, "CONFIRMACION");
        emailService.emailDesignacionAsistente1(designation, "CONFIRMACION");
        emailService.emailDesignacionAsistente2(designation, "CONFIRMACION");

        return designation;
    }

    @Override
    public void cancel(Designation designation) throws Exception {
        if (designation.getStatus().equals("CANCELADA"))
            throw new Exception("La designacion ya ha sido cancelada.");
        designation.setStatus("CANCELADA");
        designationRepository.save(designation);
        emailService.emailDesignacionArbitro(designation, "CANCELACION");
        emailService.emailDesignacionAsistente1(designation, "CANCELACION");
        emailService.emailDesignacionAsistente2(designation, "CANCELACION");
    }

    @Override
    public Designation update(Designation designation, String id) throws Exception {
        Designation designationCancelled = designationRepository.findById(id)
                .orElseThrow(() -> new Exception("No se ha encontrado la designación: " + id));
        cancel(designationCancelled);
        designation.setId(id);
        return assign(designation);

    }


}
