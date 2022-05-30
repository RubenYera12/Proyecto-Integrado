package com.ruben.rfaf.designation.application;

import com.ruben.rfaf.designation.infrastructure.repository.DesignationRepository;
import com.ruben.rfaf.designation.domain.Designation;
import com.ruben.rfaf.match.infrastructure.repository.GameRepository;
import com.ruben.rfaf.referee.infrastructure.repository.RefereeRepository;
import com.ruben.rfaf.shared.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;
    private final GameRepository gameRepository;
    private final RefereeRepository refereeRepository;
    private final EmailService emailService;

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
        designation.setMatch(gameRepository
                .findById(designation.getMatch().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el partido.")));
        designation.setMainReferee(refereeRepository
                .findById(designation.getMainReferee().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el arbitro.")));
        designation.setAssistantReferee1(refereeRepository
                .findById(designation.getAssistantReferee1().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el asistente 1.")));
        designation.setAssistantReferee2(refereeRepository
                .findById(designation.getAssistantReferee2().getId())
                .orElseThrow(()->new Exception("No se ha encontrado el asistente 2.")));
        //Comprobamos que los arbitros no esten repetidos
        if (designation.getMainReferee().getEmail().equals(designation.getAssistantReferee1().getEmail()) ||
                designation.getMainReferee().getEmail().equals(designation.getAssistantReferee2().getEmail()) ||
                designation.getAssistantReferee1().getEmail().equals(designation.getAssistantReferee2().getEmail())) {
            throw new Exception("Arbitro repetido.");
        }
        //Comprobamos que esté aceptada
        if (designation.getStatus() != null) {
            if (designation.getStatus().equals("ACEPTADA"))
                throw new Exception("La designacion ya ha sido aceptada.");
        }
        designation.setStatus("ACEPTADA");

        designation = designationRepository.save(designation);

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
